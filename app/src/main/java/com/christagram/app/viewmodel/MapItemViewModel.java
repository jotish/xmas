package com.christagram.app.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.christagram.app.R;
import com.christagram.app.data.Business;
import com.christagram.app.transitions.TransitionUtils;
import com.christagram.app.view.OnPlaceSelectedListener;

import java.util.List;

public class MapItemViewModel extends BaseObservable {

  private Context mContext;
  private Business mItem;
  private int mPosition;
  private OnPlaceSelectedListener mOnPlaceSelectedListener;

  public MapItemViewModel(Context context, Business item, int position,
                          OnPlaceSelectedListener onPlaceSelectedListener) {
    mContext = context;
    mItem = item;
    mPosition = position;
    mOnPlaceSelectedListener = onPlaceSelectedListener;
  }

  public String getItemName() {
    return mItem.getName();
  }


  public String getLocationName() {
    List<String> addressList = mItem.getLocation().getDisplayAddress();
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


  public long getRating() {
    return  Math.round(Double.valueOf(mItem.getRating()));
  }

  public String getPosition(){
    return String.valueOf(mPosition  + 1);
  }

  @BindingAdapter({"bind:imageUrl","bind:placeHolderDrawable", "bind:errorDrawable" })
  public static void setImageUrl(ImageView imageView, String imageUrl, Drawable placeHolder,
      Drawable
      errorDrawable) {
    Glide.with(imageView.getContext())
        .load(imageUrl)
        .placeholder(placeHolder)
        .fitCenter()
        .fallback(placeHolder)
        .error(errorDrawable)
        .into(imageView);
  }

  public String getMediaImageUrl() {
    if (mItem.getImageUrl() != null) {
        return  mItem.getImageUrl();
    }
    return null;
  }

  public void setItemAndPosition(Business item, int position) {
    this.mItem = item;
    this.mPosition = position;
    notifyChange();
  }

  public void onItemClick(View view){
    mOnPlaceSelectedListener.onPlaceClicked(view,
        TransitionUtils.getRecyclerViewTransitionName(mPosition), mPosition, mItem);
  }
  public String getOpenNowText(){
      if (mItem.getIsClosed()) {
         return mContext.getResources().getString(R.string.is_closed);
      } else {
         return mContext.getResources().getString(R.string.is_open);
      }
  }
}
