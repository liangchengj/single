package com.meyoustu.amuse.annotation.res;






import android.support.annotation.AnimRes;
import android.support.annotation.Keep;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Liangcheng Juves
 * Created at 2020/6/2 11:13
 */
@Keep
@Retention(RUNTIME)
@Target(FIELD)
public @interface AAnimation {
    @AnimRes int value() default -1;
}
