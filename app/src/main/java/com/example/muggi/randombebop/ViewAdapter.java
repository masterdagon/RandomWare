package com.example.muggi.randombebop;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by Michael on 08/12/15.
 */
public class ViewAdapter extends FragmentPagerAdapter {

    public Fragment f1;
    public Fragment f2;
    public FragmentManager fm;
    public FragmentTransaction ft;
    private int last_pos = -1;

    public ViewAdapter(FragmentManager fm) throws Exception{
        super(fm);
        if(fm == null){
            throw new Exception("FragmentManager findes ikke");
        }

        this.fm = fm;

        f1 = FragmentOne.newInstance("f1");
        f2 = FragmentTwo.newInstance("f2");

    }

    @Override
    public Fragment getItem(int position) {

        int changedPosition;
        changedPosition = position % 2;


        switch (changedPosition) {
            case 0:
                f1 = FragmentOne.newInstance("f1");
                    return f1;

            case 1:
                f2 = FragmentTwo.newInstance("f2");
                    return f2;
            default:
                    f1 = FragmentOne.newInstance("f1");
                    return f1;
        }


    }


    @Override
    public int getCount() {
        return 100;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return "OBJECT " + (position + 1);
    }
}
