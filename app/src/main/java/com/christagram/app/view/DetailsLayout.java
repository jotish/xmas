package com.christagram.app.view;

/**
 * Created by jotishsuthar on 29/07/17.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.christagram.app.R;
import com.christagram.app.data.Business;
import com.christagram.app.data.Coordinates;
import com.christagram.app.ui.HideDetailsTransitionSet;
import com.christagram.app.ui.ShowDetailsTransitionSet;

import java.util.List;

public class DetailsLayout extends CoordinatorLayout {

  public CardView cardViewContainer;
  public ImageView placeImageView;
  public TextView titleView;
  TextView mLocationName;
  TextView mOpenNowTextView;
  RatingBar mRating;
  private Button mTakeMeThere;

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

}
