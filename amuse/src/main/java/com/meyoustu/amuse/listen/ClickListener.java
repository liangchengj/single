package com.meyoustu.amuse.listen;

import android.view.View;

import androidx.annotation.IdRes;

/**
 * Created at 2020/6/14 10:16.
 *
 * @author Liangcheng Juves
 */
public interface ClickListener {

  void initialize(View v, @IdRes int vId);

  void onTouchDown(View v, @IdRes int vId);

  void onTouchUp(View v, @IdRes int vId);

  void onClick(View v, @IdRes int vId);
}
