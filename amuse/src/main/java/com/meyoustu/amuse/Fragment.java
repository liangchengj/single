package com.meyoustu.amuse;

import android.os.Bundle;

import androidx.annotation.Nullable;

import java.lang.reflect.ParameterizedType;

/**
 * Created at 2020/6/14 10:16.
 *
 * @author Liangcheng Juves
 */
public abstract class Fragment<T> extends androidx.fragment.app.Fragment {

  public final T fragAt;

  protected Fragment() {
    fragAt =
        (T)
            ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0]
                .getClass()
                .cast(getActivity());
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }
}
