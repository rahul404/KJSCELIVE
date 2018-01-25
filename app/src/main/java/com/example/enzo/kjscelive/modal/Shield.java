package com.example.enzo.kjscelive.modal;

import android.content.Context;

import com.example.enzo.kjscelive.R;

/**
 * Created by enzo on 12/16/2017.
 */

public class Shield extends Fest{
    Shield(Context context){
        mName = context.getString(R.string.shield);
        mDescription = context.getString(R.string.shield_hello);
    }
    public static Shield newInstance(Context context){
        Shield shield = new Shield(context);
        return shield;
    }
}
