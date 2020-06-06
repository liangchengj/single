package com.meyoustu.amuse.annotation.res;







import android.support.annotation.Keep;
import android.support.annotation.StringRes;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Liangcheng Juves
 * Created at 2020/6/2 8:56
 */
@Keep
@Retention(RUNTIME)
@Target(FIELD)
public @interface AString {
    @StringRes int value() default -1;
}
