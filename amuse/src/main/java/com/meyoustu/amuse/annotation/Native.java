package com.meyoustu.amuse.annotation;

// import android.support.annotation.Keep;

import androidx.annotation.Keep;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/** @author Liangcheng Juves Created at 2020/6/2 14:02 */
@Keep
@Retention(RUNTIME)
@Target(TYPE)
public @interface Native {
  String[] value() default {""};
}
