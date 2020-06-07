package com.meyoustu.amuse.annotation.res;


import androidx.annotation.IdRes;
import androidx.annotation.Keep;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Liangcheng Juves
 * Created at 2020/6/1 20:56
 */
@Keep
@Retention(RUNTIME)
@Target(FIELD)
public @interface AView {
    @IdRes int value() default -1;
}
