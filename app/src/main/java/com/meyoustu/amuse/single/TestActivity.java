package com.meyoustu.amuse.single;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * @author Liangcheng Juves
 * Created at 2020/6/10 17:03
 */
public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        final MainFragment mainFragment = MainFragment.newInstance();
//
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.add(R.id.frag_main, mainFragment, "FRAG_MAIN");
//        ft.commit();

//        Toast.makeText(mainFragment.fragAt, "SOS", Toast.LENGTH_LONG).show();
    }
}
