package com.meyoustu.amuse.annotation;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
import static android.view.View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
import static android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
import static android.view.View.SYSTEM_UI_FLAG_VISIBLE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/** @author Liangcheng Juves Created at 2020/6/7 14:52 */
@Retention(RUNTIME)
@Inherited
@Target(TYPE)
public @interface DecorViewConfig {
  int value() default -1;

  int HIDE_SYS_BARS =
      SYSTEM_UI_FLAG_VISIBLE
          | SYSTEM_UI_FLAG_FULLSCREEN
          | SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
          | SYSTEM_UI_FLAG_HIDE_NAVIGATION
          | SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
          | SYSTEM_UI_FLAG_IMMERSIVE_STICKY
          | SYSTEM_UI_FLAG_LAYOUT_STABLE;

  int LIGHT_STATUS_BAR =
      SYSTEM_UI_FLAG_VISIBLE | SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | SYSTEM_UI_FLAG_LAYOUT_STABLE;

  int LIGHT_NAVIGATION_BAR =
      SYSTEM_UI_FLAG_VISIBLE | SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR | SYSTEM_UI_FLAG_LAYOUT_STABLE;

  int LIGHT_SYS_BARS =
      SYSTEM_UI_FLAG_VISIBLE
          | SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
          | SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
          | SYSTEM_UI_FLAG_LAYOUT_STABLE;
}
