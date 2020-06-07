package com.meyoustu.amuse.graphics;

/**
 * @author Liangcheng Juves
 * Created at 2020/6/7 14:35
 */
public class Color extends android.graphics.Color {

    public static final int VALUE_MONOCHROME_MAXIMUM = 255;

    public static int valueOf(int alphaVal, int grayVal) {
        return Color.argb(alphaVal, grayVal, grayVal, grayVal);
    }

    public static int valOf(int value) {
        return valueOf(VALUE_MONOCHROME_MAXIMUM, value);
    }
}
