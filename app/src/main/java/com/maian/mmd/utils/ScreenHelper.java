package com.maian.mmd.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by ashan on 2016/10/13.
 */

public class ScreenHelper {
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int getScreenWidth(Activity active) {
        DisplayMetrics dm = active.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight(Activity active) {
        DisplayMetrics dm = active.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    public static LinearLayout.LayoutParams setItemPix(Activity activity, View view) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        int sd = ScreenHelper.getScreenHeight(activity) / 11;
        params.height = sd;
        params.weight = sd;
        view.setLayoutParams(params);
        return params;
    }

    public static LinearLayout.LayoutParams setgrildViewItemPix(Activity activity, View view) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        int sd = ScreenHelper.getScreenHeight(activity)/4;
        params.weight = sd;
        view.setLayoutParams(params);
        return params;
    }
}
