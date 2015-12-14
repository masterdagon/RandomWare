package com.example.muggi.randombebop;


import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.muggi.randombebop.R;

/**
 * Created by Michael on 07/12/15.
 */
public class FragmentTwo extends Fragment {

    public static final String ARG_OBJECT = "FRAGMENT2";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.fragment_two, container, false);
        Bundle args = getArguments();
        TextView tw = (TextView) rootView.findViewById(R.id.fragmentTitle);
        tw.setText(args.getString("msg"));
        return rootView;
    }

    public static Fragment newInstance(String text) {

        Fragment f = new FragmentTwo();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
}
