package com.meyoustu.amuse.annotation.res;







import android.support.annotation.IdRes;
import android.support.annotation.Keep;

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
