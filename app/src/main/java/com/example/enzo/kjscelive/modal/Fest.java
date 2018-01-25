package com.example.enzo.kjscelive.modal;

import com.example.enzo.kjscelive.R;

/**
 * Created by enzo on 12/16/2017.
 */

public class Fest {
    protected String mName;
    protected String mDescription;
    protected int mImage= R.mipmap.ic_launcher;
    protected int mLogo = R.mipmap.ic_fav_orange;
    public Fest(){
    }
    public void setLogo(int logo) {
        mLogo = logo;
    }

    public int getLogo() {
        return mLogo;
    }
    public void setName(String name) {
        mName = name;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public void setImage(int image) {
        mImage = image;
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getImage() {
        return mImage;
    }
}
