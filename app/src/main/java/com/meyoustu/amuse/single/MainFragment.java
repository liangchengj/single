package com.meyoustu.amuse.single;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.meyoustu.amuse.Fragment;

/**
 * Created at 2020/6/10 15:49.
 *
 * @author Liangcheng Juves
 */
public class MainFragment extends Fragment {

  @Nullable
  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.frag_main, container, false);
  }

  public static final MainFragment newInstance() {
    return new MainFragment();
  }
}
