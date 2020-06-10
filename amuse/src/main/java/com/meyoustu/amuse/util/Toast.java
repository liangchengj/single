package com.meyoustu.amuse.util;

import android.content.Context;

import androidx.annotation.IdRes;
import androidx.annotation.IntDef;
import androidx.annotation.StringRes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;

/**
 * @author Liangcheng Juves
 * Created at 2020/6/1 21:06
 * <p>
 * Simplify the usage of Toast that comes with Android.
 */
public final class Toast {

    /**
     * @hide
     */
    @IntDef(value = {LENGTH_SHORT, LENGTH_LONG})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {
    }

    static {
        System.loadLibrary("amuse");
    }

    private Toast() {
    }

    public static void showShort(Context ctx, Object msg) {
        toastMsg(ctx, msg, LENGTH_SHORT);
    }

    public static void showShort(Context ctx, @StringRes int id) {
        toastRes(ctx, id, LENGTH_SHORT);
    }

    public static void showLong(Context ctx, Object msg) {
        toastMsg(ctx, msg, LENGTH_LONG);
    }


    public static void showLong(Context ctx, @StringRes int id) {
        toastRes(ctx, id, LENGTH_LONG);
    }

    static native void toastMsg(Context ctx, Object msg, @Duration int duration);

    static native void toastRes(Context ctx, @StringRes int id, @Duration int duration);

}
