package com.meyoustu.amuse.annotation.sysbar;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created at 2020/6/13 14:14.
 *
 * @author Liangcheng Juves
 */
@Retention(RUNTIME)
@Inherited
@Target(TYPE)
public @interface WindowFullScreen {}
