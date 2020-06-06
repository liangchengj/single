package com.meyoustu.amuse.annotation;


import android.support.annotation.Keep;
import android.support.annotation.LayoutRes;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Liangcheng Juves
 * Created at 2020/6/2 12:29
 */
@Keep
@Retention(RUNTIME)
@Inherited
@Target(TYPE)
public @interface ContentView {
    @LayoutRes int value() default -1;
}
