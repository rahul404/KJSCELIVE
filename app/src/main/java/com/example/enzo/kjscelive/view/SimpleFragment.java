package com.example.enzo.kjscelive.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by enzo on 1/31/2018.
 */

public class SimpleFragment extends Fragment{
    private static final String ARGUMENT_KEY = "com.example.enzo.kjscelive.view.SimpleFragment.LayoutArgument";

    public static SimpleFragment newInstance(int id){
        Bundle args = new Bundle();
        args.putInt(ARGUMENT_KEY,id);
        SimpleFragment f =  new SimpleFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView( LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        Bundle args = getArguments();
        if(args != null){
            int layout = args.getInt(ARGUMENT_KEY);
            View v = inflater.inflate(layout,container,false);
            return v;
        }
        return null;
    }
}
