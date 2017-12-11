package com.christagram.app.viewmodel;


import android.content.Context;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.view.View;

import com.christagram.app.ChristmasApplication;
import com.christagram.app.R;
import com.christagram.app.data.Business;
import com.christagram.app.data.Coordinates;
import com.christagram.app.data.Markets;
import com.christagram.app.repo.MarketsRepo;
import com.christagram.app.utils.Utils;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class FragmentMapViewModel extends Observable {

  private Context mContext;
  public ObservableInt listProgress;
  public ObservableInt itemContainer;
  private CompositeDisposable compositeDisposable = new CompositeDisposable();
  private List<Business> mProperties;

  public FragmentMapViewModel(@NonNull Context context) {
    mContext = context;
    mProperties = new ArrayList<>();
    listProgress = new ObservableInt(View.GONE);
    itemContainer = new ObservableInt(View.GONE);
    compositeDisposable = new CompositeDisposable();
  }

  public void init() {
    initializeViews();
    getItemList();
  }

  public void initializeViews() {
    itemContainer.set(View.GONE);
    listProgress.set(View.VISIBLE);
  }

  public void showList() {
    listProgress.set(View.GONE);
    itemContainer.set(View.VISIBLE);
  }

  public void showFailureState() {
    listProgress.set(View.GONE);
    itemContainer.set(View.GONE);
    Utils.showNotificationToast(mContext, mContext.getString(R.string.error_network));
  }

  private void getItemList() {
    ChristmasApplication realEstateApplication = ChristmasApplication.create(mContext);
    MarketsRepo itemService = realEstateApplication.getMarketRepo();

    Disposable disposable = itemService.fetchMarkets("Munich")
        .subscribeOn(realEstateApplication.subscribeScheduler())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<Markets>() {
          @Override public void accept(Markets itemResponse) throws Exception {
            showList();
            changeItemListSet(itemResponse.getBusinesses());
          }
        }, new Consumer<Throwable>() {
          @Override public void accept(Throwable throwable) throws Exception {
            showFailureState();
          }
        });
    compositeDisposable.add(disposable);
  }

  private void changeItemListSet(List<Business> items) {
    mProperties.addAll(items);
    setChanged();
    notifyObservers();
  }


  public void reset() {
    unSubscribeFromObservable();
    compositeDisposable = null;
    mContext = null;
  }
  private void unSubscribeFromObservable() {
    if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
      compositeDisposable.dispose();
    }
  }

  public List<Business> getProperties() {
    return mProperties;
  }

  public LatLngBounds provideLatLngBoundsForAllPlaces() {
    LatLngBounds.Builder builder = new LatLngBounds.Builder();
    for(Business property : mProperties) {
      Coordinates location = property.getCoordinates();
      builder.include(new LatLng(Double.valueOf(location.getLatitude()),
              Double.valueOf(location.getLongitude())));
    }
    return builder.build();
  }
}
