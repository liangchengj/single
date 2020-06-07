package com.meyoustu.amuse.util;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.IdRes;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

/**
 * @author Liangcheng Juves
 * Created at 2020/6/1 21:06
 * <p>
 * Simplify the usage of Toast that comes with Android.
 */
public final class Toast {
    private Toast() {
    }

    public static void showShort(Context ctx, Object msg) {
        makeText(ctx, msg.toString(), LENGTH_SHORT).show();
    }

    @SuppressLint("ResourceType")
    public static void showShort(Context ctx, @IdRes int id) {
        makeText(ctx, id, LENGTH_SHORT).show();
    }

    public static void showLong(Context ctx, Object msg) {
        makeText(ctx, msg.toString(), LENGTH_LONG).show();
    }


    @SuppressLint("ResourceType")
    public static void showLong(Context ctx, @IdRes int id) {
        makeText(ctx, id, LENGTH_LONG).show();
    }

}
