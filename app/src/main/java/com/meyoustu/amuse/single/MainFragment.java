package com.meyoustu.amuse.single;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.meyoustu.amuse.Activity;
import com.meyoustu.amuse.Fragment;

/**
 * @author Liangcheng Juves
 * Created at 2020/6/10 15:49
 */
public class MainFragment extends Fragment<Activity> {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main, container, false);
    }
}
