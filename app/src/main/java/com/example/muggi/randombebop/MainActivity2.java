package com.example.muggi.randombebop;


import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.muggi.randombebop.R;

public class MainActivity2 extends FragmentActivity {

    ViewPager viewPager;
    ViewAdapter viewAdapter;
    ActionBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewAdapter = new ViewAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewAdapter);

    }

    public void onSaveInstanceState(Bundle outState){

        super.onSaveInstanceState(outState);
        outState.putInt("tab", viewPager.getCurrentItem());
    }

    public void onRestoreInstanceState(Bundle inState){

        viewPager.setCurrentItem(inState.getInt("tab"));
    }
}
