package com.christagram.app;

import android.app.Application;
import android.content.Context;

import com.christagram.app.repo.ChristmasFactory;
import com.christagram.app.repo.MarketsRepo;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jotishs on 12/11/17.
 */

public class ChristmasApplication extends Application {

    private MarketsRepo mMarketsRepo;
    private Scheduler mScheduler;

    private static ChristmasApplication get(Context context) {
        return (ChristmasApplication) context.getApplicationContext();
    }

    public static ChristmasApplication create(Context context) {
        return ChristmasApplication.get(context);
    }

    public MarketsRepo getMarketRepo() {
        if (mMarketsRepo == null) {
            mMarketsRepo = ChristmasFactory.create();
        }
        return mMarketsRepo;
    }

    public Scheduler subscribeScheduler() {
        if (mScheduler == null) {
            mScheduler = Schedulers.io();
        }

        return mScheduler;
    }
}
