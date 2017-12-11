package com.christagram.app.view;

import android.view.View;

import com.christagram.app.data.Business;

/**
 * Created by jotishsuthar on 29/07/17.
 */

public interface OnPlaceSelectedListener {
  void onPlaceClicked(View sharedView, String transitionName, final int position, final Business place);
}
