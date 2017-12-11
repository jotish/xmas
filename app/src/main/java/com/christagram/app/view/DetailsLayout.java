package com.christagram.app.view;

/**
 * Created by jotishsuthar on 29/07/17.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.CardView;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.christagram.app.ChristmasApplication;
import com.christagram.app.R;
import com.christagram.app.data.Business;
import com.christagram.app.data.Coordinates;
import com.christagram.app.data.venues.Category;
import com.christagram.app.data.venues.Explore;
import com.christagram.app.data.venues.Group;
import com.christagram.app.data.venues.Item;
import com.christagram.app.repo.MarketsRepo;
import com.christagram.app.ui.HideDetailsTransitionSet;
import com.christagram.app.ui.ShowDetailsTransitionSet;
import com.christagram.app.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class DetailsLayout extends CoordinatorLayout{

  public CardView cardViewContainer;
  public ImageView placeImageView;
  public TextView titleView;
  TextView mLocationName;
  TextView mOpenNowTextView;
  RatingBar mRating;
  private Button mTakeMeThere;
  private Button mSendPostCard;
  private CompositeDisposable compositeDisposable = new CompositeDisposable();
  private TextView mVenues;

  public DetailsLayout(final Context context) {
    this(context, null);
  }

  public DetailsLayout(final Context context, final AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    cardViewContainer = (CardView) findViewById(R.id.cardview);
    placeImageView = (ImageView) findViewById(R.id.headerImage);
    titleView = (TextView) findViewById(R.id.title);
    mLocationName = (TextView) findViewById(R.id.location);
    mOpenNowTextView = (TextView) findViewById(R.id.opening);
    mRating = (RatingBar) findViewById(R.id.rating);
    mTakeMeThere = (Button) findViewById(R.id.takeMe);
    mSendPostCard = (Button) findViewById(R.id.sendPostCard);
    mVenues = (TextView) findViewById(R.id.venues);
  }

  private void setData(final  Business place) {
    titleView.setText(place.getName());
    mLocationName.setText(getLocationName(place));
    mOpenNowTextView.setText(getOpenNowText(place));
    mRating.setRating(getRating(place));
    if (place.getImageUrl() != null) {
      Glide.with(placeImageView.getContext())
          .load(place.getImageUrl())
          .fitCenter()
          .into(placeImageView);
    }
    mTakeMeThere.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        String coordinatesText = "";
        Coordinates coordinates = place.getCoordinates();
        coordinatesText = coordinates.getLatitude() + "," +coordinates.getLongitude();
        startNavigation(coordinatesText);
      }
    });
    mSendPostCard.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        startPostCard();
      }
    });
    getExploreList(place);
  }

  public static Scene showScene(Activity activity, final ViewGroup container,
      final View sharedView,
      final String transitionName, final Business data) {
    DetailsLayout detailsLayout = (DetailsLayout) activity.getLayoutInflater()
        .inflate(R.layout.item_place, container, false);
    detailsLayout.setData(data);

    TransitionSet set = new ShowDetailsTransitionSet(
        activity, transitionName, sharedView, detailsLayout);
    Scene scene = new Scene(container, (View) detailsLayout);
    TransitionManager.go(scene, set);
    return scene;
  }

  public static Scene hideScene(Activity activity, final ViewGroup container,
      final View sharedView, final String transitionName) {
    DetailsLayout detailsLayout = (DetailsLayout)
        container.findViewById(R.id.bali_details_container);

    TransitionSet set = new HideDetailsTransitionSet(
        activity, transitionName, sharedView, detailsLayout);
    Scene scene = new Scene(container, (View) detailsLayout);
    TransitionManager.go(scene, set);
    return scene;
  }

  public String getOpenNowText(Business business){
    if (business.getIsClosed()) {
      return getContext().getResources().getString(R.string.is_closed);
    } else {
      return getContext().getResources().getString(R.string.is_open);
    }
  }
  public long getRating(Business business) {
    return  Math.round(Double.valueOf(business.getRating()));
  }

  public String getLocationName(Business place) {
    List<String> addressList = place.getLocation().getDisplayAddress();
    String fineAddress = "";
    int i = 0;
    for (String address : addressList) {
      if (i == 0) {
        fineAddress = address;
      } else {
        fineAddress = fineAddress + " , " + address;
      }
      i++;
    }
    return fineAddress;
  }
 public void startNavigation(String latLong) {
   Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
           Uri.parse("http://maps.google.com/maps?daddr="+latLong));
   ((Activity)getContext()).startActivity(intent);
 }

  public void startPostCard() {
    PackageManager packageManager = getContext().getPackageManager();
    String inkPackage = "com.sincerely.android.ink";
    if (Utils.isPackageInstalled(inkPackage, packageManager)) {
      ((Activity)getContext()).startActivity(packageManager.getLaunchIntentForPackage(inkPackage));
    } else {
      Utils.startPlaystore((Activity)getContext(), inkPackage);
    }
  }

  private void getExploreList(Business place) {
    ChristmasApplication realEstateApplication = ChristmasApplication.create(getContext());
    MarketsRepo itemService = realEstateApplication.getMarketRepo();
    Coordinates coordinates = place.getCoordinates();
    String latLong = coordinates.getLatitude() + "," + coordinates.getLongitude();
    Disposable disposable = itemService.fetchExplorations(latLong, 500)
            .subscribeOn(realEstateApplication.subscribeScheduler())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<Explore>() {
              @Override public void accept(Explore itemResponse) throws Exception {
                if (itemResponse != null && itemResponse.getResponse() != null
                        && itemResponse.getResponse().getGroups() != null)
                   showExtraVenues(itemResponse.getResponse().getGroups());
              }
            }, new Consumer<Throwable>() {
              @Override public void accept(Throwable throwable) throws Exception {
                showFailureState();
              }
            });
    compositeDisposable.add(disposable);
  }

  public void showExtraVenues(List<Group> groups) {
      List<String> venues = new ArrayList<>();
      for(Group group : groups) {
         if(group.getItems() != null ) {
             for (Item item: group.getItems()) {
                 if (item.getVenue() != null) {
                     String venue = item.getVenue().getName();
                     if (item.getVenue().getCategories().size()> 0 ) {
                       Category category = item.getVenue().getCategories().get(0);
                       venue = venue + " - " + category.getName();
                     }
                     venues.add(venue);
                 }
             }
         }
      }
      String venuesAppended = "Things to do:\n\n";
      int i = 1;
      for(String venue : venues) {
          if (i > 4) {
              break;
          }
          venuesAppended = venuesAppended + i + ") "+ venue + "\n";
          i++;
      }

      mVenues.setText(venuesAppended);
  }

  private void unSubscribeFromObservable() {
    if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
      compositeDisposable.dispose();
    }
  }
  public void showFailureState() {
    Utils.showNotificationToast(getContext(), getContext().getString(R.string.error_network));
  }

}
