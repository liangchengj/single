package com.meyoustu.amuse;

import android.content.Context;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

/**
 * @author Liangcheng Juves
 * Created at 2020/6/1 21:06
 */
public final class Toast {
    private Toast() {
    }

    public static void showShort(Context ctx, Object msg) {
        makeText(ctx, msg.toString(), LENGTH_SHORT).show();
    }

    public static void showShort(Context ctx, byte msg) {
        makeText(ctx, "" + msg, LENGTH_SHORT).show();
    }

    public static void showShort(Context ctx, short msg) {
        makeText(ctx, "" + msg, LENGTH_SHORT).show();
    }

    public static void showShort(Context ctx, int msg) {
        makeText(ctx, "" + msg, LENGTH_SHORT).show();
    }

    public static void showShort(Context ctx, float msg) {
        makeText(ctx, "" + msg, LENGTH_SHORT).show();
    }

    public static void showShort(Context ctx, double msg) {
        makeText(ctx, "" + msg, LENGTH_SHORT).show();
    }

    public static void showShort(Context ctx, boolean msg) {
        makeText(ctx, "" + msg, LENGTH_SHORT).show();
    }

    public static void showShort(Context ctx, char msg) {
        makeText(ctx, "" + msg, LENGTH_SHORT).show();
    }

    public static void showShortWithResId(Context ctx, int resId) {
        makeText(ctx, resId, LENGTH_SHORT).show();
    }

    public static void showLong(Context ctx, Object msg) {
        makeText(ctx, msg.toString(), LENGTH_LONG).show();
    }

    public static void showLong(Context ctx, byte msg) {
        makeText(ctx, "" + msg, LENGTH_LONG).show();
    }

    public static void showLong(Context ctx, short msg) {
        makeText(ctx, "" + msg, LENGTH_LONG).show();
    }

    public static void showLong(Context ctx, int msg) {
        makeText(ctx, "" + msg, LENGTH_LONG).show();
    }

    public static void showLong(Context ctx, long msg) {
        makeText(ctx, "" + msg, LENGTH_LONG).show();
    }

    public static void showLong(Context ctx, float msg) {
        makeText(ctx, "" + msg, LENGTH_LONG).show();
    }

    public static void showLong(Context ctx, double msg) {
        makeText(ctx, "" + msg, LENGTH_LONG).show();
    }

    public static void showLong(Context ctx, boolean msg) {
        makeText(ctx, "" + msg, LENGTH_LONG).show();
    }

    public static void showLong(Context ctx, char msg) {
        makeText(ctx, "" + msg, LENGTH_LONG).show();
    }

    public static void showLongWithResId(Context ctx, int resId) {
        makeText(ctx, resId, LENGTH_LONG).show();
    }

}
