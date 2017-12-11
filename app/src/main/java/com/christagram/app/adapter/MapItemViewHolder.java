package com.christagram.app.adapter;

import android.support.v7.widget.RecyclerView;

import com.christagram.app.data.Business;
import com.christagram.app.databinding.MapItemLayoutBinding;
import com.christagram.app.view.OnPlaceSelectedListener;
import com.christagram.app.viewmodel.MapItemViewModel;


public class MapItemViewHolder extends RecyclerView.ViewHolder {
  MapItemLayoutBinding mMapItemLayoutBinding;
  private OnPlaceSelectedListener mOnPlaceSelectedListener;

  public MapItemViewHolder(MapItemLayoutBinding itemBinding,
      OnPlaceSelectedListener onPlaceSelectedListener) {
    super(itemBinding.itemView);
    this.mMapItemLayoutBinding = itemBinding;
    this.mOnPlaceSelectedListener = onPlaceSelectedListener;
  }

  public void bindItem(Business item, int position) {
    if (mMapItemLayoutBinding.getMapItemViewModel() == null) {
      mMapItemLayoutBinding.setMapItemViewModel(
          new MapItemViewModel(itemView.getContext(),item , position, mOnPlaceSelectedListener));
    } else {
      mMapItemLayoutBinding.getMapItemViewModel().setItemAndPosition(item, position);
    }
  }
}
