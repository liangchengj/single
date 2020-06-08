package com.meyoustu.amuse.annotation;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Liangcheng Juves
 * Created at 2020/6/8 20:11
 */
@Retention(RUNTIME)
@Inherited
@Target(TYPE)
public @interface IntelliRes {
}
