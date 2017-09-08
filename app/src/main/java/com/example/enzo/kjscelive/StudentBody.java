package com.example.enzo.kjscelive;

/**
 * Created by enzo on 8/31/2017.
 */

public class StudentBody {
    private String mName;
    private String mDescription;
    private int mImage=R.drawable.ic_launcher;
    public StudentBody(){

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
