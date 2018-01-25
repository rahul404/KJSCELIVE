package com.example.enzo.kjscelive.modal;

import android.content.Context;

import com.example.enzo.kjscelive.R;

/**
 * Created by enzo on 12/12/2017.
 */

public class CulturalStudentBodyList extends StudentBodyList<CulturalStudentBody>{
    private static CulturalStudentBodyList mCulturalStudentBodyList;
    private CulturalStudentBodyList(Context context) {
        super(context);

        //code to add councils
        CulturalStudentBody body = new CulturalStudentBody();
        body.setName(mContext.getString(R.string.insignia));
        body.setDescription(mContext.getString(R.string.insignia_hello));
        add(body);
        body = new CulturalStudentBody();
        body.setName(mContext.getString(R.string.rhapsody));
        body.setDescription(mContext.getString(R.string.rhapsody_hello));
        add(body);
        body = new CulturalStudentBody();
        body.setName(mContext.getString(R.string.gyrations));
        body.setDescription(mContext.getString(R.string.gyrations_hello));
        add(body);
        body = new CulturalStudentBody();
        body.setName(mContext.getString(R.string.octavium));
        body.setDescription(mContext.getString(R.string.octavium_hello));
        add(body);
        body = new CulturalStudentBody();
        body.setName(mContext.getString(R.string.illuminati));
        body.setDescription(mContext.getString(R.string.illuminati_hello));
        add(body);
    }

    public static CulturalStudentBodyList getInstance(Context context){
        if(mCulturalStudentBodyList == null){
            mCulturalStudentBodyList = new CulturalStudentBodyList(context);
        }
        return mCulturalStudentBodyList;
    }
    @Override
    public int getCategory() {
        return CATEGORY_CULTURAL;
    }
}
