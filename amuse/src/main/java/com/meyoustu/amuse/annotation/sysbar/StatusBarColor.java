package com.meyoustu.amuse.annotation.sysbar;

import androidx.annotation.ColorInt;
import androidx.annotation.Keep;

import com.meyoustu.amuse.graphics.Color;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Liangcheng Juves
 * @info Created at 2020/6/2 11:13
 */
@Keep
@Retention(RUNTIME)
@Inherited
@Target(TYPE)
public @interface StatusBarColor {
  @ColorInt
  int value() default Color.WHITE;
}
