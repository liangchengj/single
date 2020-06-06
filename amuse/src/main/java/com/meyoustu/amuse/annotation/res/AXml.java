package com.meyoustu.amuse.annotation.res;


import android.support.annotation.Keep;
import android.support.annotation.XmlRes;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Liangcheng Juves
 * Created at 2020/6/2 12:24
 */
@Keep
@Retention(RUNTIME)
@Target(FIELD)
public @interface AXml {
    @XmlRes int value() default -1;
}
