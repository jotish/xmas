package com.christagram.app.view;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Scene;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.christagram.app.R;
import com.christagram.app.adapter.MapItemAdapter;
import com.christagram.app.data.Business;
import com.christagram.app.data.Coordinates;
import com.christagram.app.databinding.FragmentItemMapBinding;
import com.christagram.app.transitions.ScaleDownImageTransition;
import com.christagram.app.ui.HorizontalRecyclerViewScrollListener;
import com.christagram.app.ui.maps.MapBitmapCache;
import com.christagram.app.ui.maps.PulseOverlayLayout;
import com.christagram.app.viewmodel.FragmentMapViewModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraIdleListener;
import com.google.android.gms.maps.GoogleMap.OnCameraMoveListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;


import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ItemsMapFragment extends Fragment implements OnMapReadyCallback, Observer,
    HorizontalRecyclerViewScrollListener.OnItemCoverListener, OnPlaceSelectedListener {

  private FragmentMapViewModel mItemMapViewModel;
  private FragmentItemMapBinding mFragmentItemMapBinding;
  private FragmentActivity mContext;
  private String currentTransitionName;
  private Scene detailsScene;

  public static ItemsMapFragment newInstance(final Context context) {
    ItemsMapFragment fragment = new ItemsMapFragment();
    ScaleDownImageTransition transition =
        new ScaleDownImageTransition(context, MapBitmapCache.instance().getBitmap());
    transition.addTarget(context.getString(R.string.mapPlaceholderTransition));
    transition.setDuration(600);
    fragment.setEnterTransition(transition);
    return fragment;
  }




  @Override
  public void onCreate(@Nullable final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mContext = getActivity();
  }

  @Nullable
  @Override
  public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
      @Nullable final Bundle savedInstanceState) {
    mFragmentItemMapBinding = FragmentItemMapBinding.inflate(inflater,
        container,false);
    mItemMapViewModel = new FragmentMapViewModel(mContext);
    mFragmentItemMapBinding.setItemMapViewModel(mItemMapViewModel);
    setupItemsView(mFragmentItemMapBinding.recyclerview, this);
    mItemMapViewModel.init();
    setupMapFragment();
    setupObserver(mItemMapViewModel);
    return mFragmentItemMapBinding.getRoot();
  }

  public void setupObserver(Observable observable) {
    observable.addObserver(this);
  }

  private void setupItemsView(RecyclerView listRecyler, OnPlaceSelectedListener onPlaceSelectedListener) {
    MapItemAdapter adapter = new MapItemAdapter(onPlaceSelectedListener);
    listRecyler.setLayoutManager(
        new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
    listRecyler.setAdapter(adapter);
    listRecyler.addOnScrollListener(new HorizontalRecyclerViewScrollListener(this));
  }

  private void setupMapFragment() {
    ((SupportMapFragment)getChildFragmentManager()
        .findFragmentById(R.id.mapFragment)).getMapAsync(this);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    mItemMapViewModel.reset();
  }

  @Override
  public void onMapReady(final GoogleMap googleMap) {
    mFragmentItemMapBinding.mapOverlayLayout.setupMap(googleMap);
  }

  private void setupMapLayout(final PulseOverlayLayout mapOverlayLayout,
                              LatLngBounds latLngBounds, final List<Business> items) {
    mapOverlayLayout.moveCamera(latLngBounds);
    mapOverlayLayout.setOnCameraIdleListener(new OnCameraIdleListener() {
      @Override
      public void onCameraIdle() {
        for (int i = 0; i < items.size(); i++) {
          Coordinates coordinates = items.get(i).getCoordinates();
          mapOverlayLayout.createAndShowMarker(i, new LatLng(coordinates.getLatitude(),
                  coordinates.getLongitude()));
        }
        mapOverlayLayout.setOnCameraIdleListener(null);
      }
    });
    mapOverlayLayout.setOnCameraMoveListener(new OnCameraMoveListener() {
      @Override
      public void onCameraMove() {
        mapOverlayLayout.refresh();
      }
    });
  }

  private void hideAllMarkers() {
    mFragmentItemMapBinding.mapOverlayLayout.setOnCameraIdleListener(null);
    mFragmentItemMapBinding.mapOverlayLayout.hideAllMarkers();
  }

  @Override
  public void update(final Observable observable, final Object o) {
    if (observable instanceof FragmentMapViewModel) {
      MapItemAdapter adapter = (MapItemAdapter) mFragmentItemMapBinding.recyclerview.getAdapter();
      FragmentMapViewModel itemMapViewModel = (FragmentMapViewModel) observable;
      List<Business> items = itemMapViewModel.getProperties();
      adapter.setItems(items);
      setupMapLayout(mFragmentItemMapBinding.mapOverlayLayout,
          mItemMapViewModel.provideLatLngBoundsForAllPlaces(), items);
    }
  }

  @Override
  public void onItemCover(final int position) {
    mFragmentItemMapBinding.mapOverlayLayout.showMarker(position);
  }

  @Override
  public void onPlaceClicked(final View sharedView, final String transitionName,
      final int position, Business place) {
    currentTransitionName = transitionName;
    detailsScene = DetailsLayout.showScene(getActivity(),
        mFragmentItemMapBinding.dataContainer, sharedView, transitionName, place);
    hideAllMarkers();
  }

}
