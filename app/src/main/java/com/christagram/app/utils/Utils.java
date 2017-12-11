package com.christagram.app.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
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
    public static boolean isPackageInstalled(String packagename, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packagename, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static void startPlaystore(Activity context, String appPackageName){
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }
}
