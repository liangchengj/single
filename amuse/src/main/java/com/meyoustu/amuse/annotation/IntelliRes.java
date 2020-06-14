package com.meyoustu.amuse.annotation;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created at 2020/6/14 10:16.
 *
 * @author Liangcheng Juves
 */
@Retention(RUNTIME)
@Inherited
@Target(TYPE)
public @interface IntelliRes {}
