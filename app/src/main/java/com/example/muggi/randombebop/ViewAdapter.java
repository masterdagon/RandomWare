package com.example.muggi.randombebop;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Michael on 08/12/15.
 */
public class ViewAdapter extends FragmentPagerAdapter {

    public ViewAdapter(FragmentManager fm){

        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        int changedPosition;
        changedPosition = position%2;
        switch(changedPosition){

            case 0: return FragmentOne.newInstance("Fragment One; instance: "+position);
            case 1: return FragmentTwo.newInstance("Fragment Two; instance: "+position);
            default: return FragmentOne.newInstance("Fragment One; default; position: "+position);
        }
    }

    @Override
    public int getCount() {
        return 100;
    }

    @Override
    public CharSequence getPageTitle(int position){

        return "OBJECT " + (position+1);
    }
}
