package com.meyoustu.amuse.annotation.sysbar;


import android.support.annotation.ColorRes;
import android.support.annotation.Keep;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Liangcheng Juves
 * Created at 2020/6/2 11:36
 */
@Keep
@Retention(RUNTIME)
@Inherited
@Target(TYPE)
public @interface NavigationBarColor {
    @ColorRes int value() default -1;
}
