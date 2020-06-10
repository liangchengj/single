package com.meyoustu.amuse;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import java.lang.reflect.ParameterizedType;

/**
 * @author Liangcheng Juves
 * Created at 2020/6/10 15:05
 */
public abstract class Fragment<T extends Activity>
        extends androidx.fragment.app.Fragment {

//    protected final T fragAt;
//    protected final Fragment fragParent = Fragment.this;

    protected Fragment() {
//        fragAt = (T) getActivity().getClass().cast(getContext());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        fragAt.onCreate(savedInstanceState);
    }

//    protected View getFragAtDecorView() {
//        return fragAt.getDecorView();
//    }
}
