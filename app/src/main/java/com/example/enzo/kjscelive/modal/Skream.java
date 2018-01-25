package com.example.enzo.kjscelive.modal;

import android.content.Context;

import com.example.enzo.kjscelive.R;

/**
 * Created by enzo on 12/16/2017.
 */

public class Skream extends Fest{
    Skream(Context context){
        mName = context.getString(R.string.skream);
        mDescription = context.getString(R.string.skream_hello);
    }
    public static Skream newInstance(Context context){
        Skream skream= new Skream(context);
        return skream;
    }
}
