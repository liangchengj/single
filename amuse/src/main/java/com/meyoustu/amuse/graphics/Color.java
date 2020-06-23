package com.meyoustu.amuse.graphics;

import androidx.annotation.ColorInt;
import androidx.core.graphics.ColorUtils;

/**
 * Created at 2020/6/14 10:16.
 *
 * @author Liangcheng Juves
 */
public class Color extends android.graphics.Color {

  public static final int VALUE_MONOCHROME_MAXIMUM = 255;

  public static @ColorInt int valueOf(int alpha, int pixel) {
    return Color.argb(alpha, pixel, pixel, pixel);
  }

  public static @ColorInt int initWith(int value) {
    return valueOf(VALUE_MONOCHROME_MAXIMUM, value);
  }

  /**
   * @param color The value of the color.
   * @return "true" means the color is bright.
   */
  public static boolean isLightColor(@ColorInt int color) {
    return ColorUtils.calculateLuminance(color) >= 0.5;
  }
}
