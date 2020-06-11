package com.meyoustu.amuse.single;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.meyoustu.amuse.App;
import com.meyoustu.amuse.content.SharedPreferences;
import com.meyoustu.amuse.util.Toast;

/**
 * @author Liangcheng Juves
 * Created at 2020/6/10 17:03
 */
public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = SharedPreferences.initialize(this);
        sharedPreferences.putNumber("Test", 1);

        Toast.showLong(this, sharedPreferences.getInt("Test", 0));

//        final MainFragment mainFragment = MainFragment.newInstance();
//
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.add(R.id.frag_main, mainFragment, "FRAG_MAIN");
//        ft.commit();

//        Toast.makeText(mainFragment.fragAt, "SOS", Toast.LENGTH_LONG).show();
    }
}
