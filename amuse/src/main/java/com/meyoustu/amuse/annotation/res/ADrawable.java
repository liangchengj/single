package com.meyoustu.amuse.annotation.res;







import android.support.annotation.DrawableRes;
import android.support.annotation.Keep;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Liangcheng Juves
 * Created at 2020/6/2 11:16
 */
@Keep
@Retention(RUNTIME)
@Target(FIELD)
public @interface ADrawable {
    @DrawableRes int value() default -1;
}
