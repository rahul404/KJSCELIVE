package com.example.enzo.kjscelive.modal;

import android.content.Context;

import com.example.enzo.kjscelive.R;

/**
 * Created by enzo on 12/16/2017.
 */

public class Kjsce extends Fest{
    Kjsce(Context context){
        mName = context.getString(R.string.about_kjsce);
        mDescription = context.getString(R.string.about_kjsce_hello);
        mLogo = R.mipmap.about_kjsce_logo;
        mImage = R.mipmap.about_kjsce_cover;
    }
    public static Kjsce newInstance(Context context){
        Kjsce kjsce = new Kjsce(context);
        return kjsce;
    }
}
