package com.meyoustu.amuse.annotation.sysbar;

import androidx.annotation.ColorRes;
import androidx.annotation.Keep;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/** @author Liangcheng Juves Created at 2020/6/2 11:34 */
@Keep
@Retention(RUNTIME)
@Inherited
@Target(TYPE)
public @interface StatusBarColor {
  @ColorRes
  int value() default -1;
}
