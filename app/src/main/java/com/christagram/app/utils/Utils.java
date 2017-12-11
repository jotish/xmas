package com.christagram.app.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by jotishsuthar on 27/07/17.
 */

public class Utils {

    public static void showNotificationToast(Context ctx, String string) {
      Toast.makeText(ctx, string, Toast.LENGTH_SHORT).show();
    }

    public static boolean isActivityAlive(Activity activity) {
      return !(null == activity || activity.isFinishing() || activity.isDestroyed());
    }
}
