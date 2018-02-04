package com.example.enzo.kjscelive.modal;

import android.content.Context;

import com.example.enzo.kjscelive.R;

/**
 * Created by enzo on 12/16/2017.
 */

public class Abhiyantriki extends Fest {
    Abhiyantriki(Context context){
        mName = context.getString(R.string.abhiyantriki);
        mDescription = context.getString(R.string.abhiyantriki_hello);
        mImage = R.mipmap.abhiyanytriki_cover;
        mLogo = R.mipmap.abhiyanytriki_logo;
    }
    public static Abhiyantriki newInstance(Context context){
        Abhiyantriki abhiyantriki = new Abhiyantriki(context);
        return abhiyantriki;
    }
}
