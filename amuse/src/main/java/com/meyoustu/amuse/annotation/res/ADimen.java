package com.meyoustu.amuse.annotation.res;


import androidx.annotation.DimenRes;
import androidx.annotation.Keep;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Liangcheng Juves
 * Created at 2020/6/2 11:28
 */
@Keep
@Retention(RUNTIME)
@Target(FIELD)
public @interface ADimen {
    @DimenRes int value() default -1;
}
