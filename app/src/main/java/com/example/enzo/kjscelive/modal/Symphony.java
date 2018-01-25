package com.example.enzo.kjscelive.modal;

import android.content.Context;

import com.example.enzo.kjscelive.R;

/**
 * Created by enzo on 12/16/2017.
 */

public class Symphony extends Fest{
    Symphony(Context context){
        mName = context.getString(R.string.symphony);
        mDescription = context.getString(R.string.symphony_hello);
    }
    public static Symphony newInstance(Context context){
        Symphony symphony = new Symphony(context);
        return symphony;
    }
}
