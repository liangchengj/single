package com.meyoustu.amuse.annotation.res;

import androidx.annotation.Keep;
import androidx.annotation.XmlRes;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created at 2020/6/14 10:16.
 *
 * @author Liangcheng Juves
 */
@Keep
@Retention(RUNTIME)
@Target(FIELD)
public @interface AXml {
  @XmlRes
  int value() default -1;
}
