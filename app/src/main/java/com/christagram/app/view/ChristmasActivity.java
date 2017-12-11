package com.christagram.app.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.christagram.app.R;
import com.christagram.app.databinding.ActivityXmasBinding;
import com.christagram.app.viewmodel.ChristMasViewModel;

/**
 * Created by jotishs on 12/11/17.
 */

public class ChristmasActivity extends AppCompatActivity {

    private ActivityXmasBinding mActivityXmasBinding;
    private ChristMasViewModel mChristMasViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBinding();
        loadListingMapFragment();
    }

    private void initDataBinding() {
        mActivityXmasBinding = DataBindingUtil.setContentView(this, R.layout.activity_xmas);
        mChristMasViewModel = new ChristMasViewModel();
        mActivityXmasBinding.setXmasModel(mChristMasViewModel);
    }

    private void loadListingMapFragment() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.content_fragment);
        if (currentFragment == null || !(currentFragment instanceof  ItemsMapFragment)) {
            ItemsMapFragment itemsMapFragment = ItemsMapFragment.newInstance(this);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            transaction.replace(R.id.content_fragment, itemsMapFragment, itemsMapFragment.getTag());
            transaction.commit();
        }
    }
}
