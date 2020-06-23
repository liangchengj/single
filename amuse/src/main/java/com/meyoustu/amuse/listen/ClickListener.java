package com.meyoustu.amuse.listen;

import android.view.MotionEvent;
import android.view.View;

/** @author Liangcheng Juves Created at 2020/6/2 16:26 */
public interface ClickListener {
  void onTouchDown(View v, MotionEvent event);

  void onTouchUp(View v, MotionEvent event);

  void onClick(View v);

  int RESP_TIME_MILLLIS = 500;
}
