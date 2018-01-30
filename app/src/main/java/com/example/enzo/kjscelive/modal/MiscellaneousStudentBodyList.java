package com.example.enzo.kjscelive.modal;

import android.content.Context;

import com.example.enzo.kjscelive.R;

/**
 * Created by enzo on 12/12/2017.
 */

public class MiscellaneousStudentBodyList extends StudentBodyList<MiscellaneousStudentBody>{
    private static MiscellaneousStudentBodyList mMiscellaneousStudentBodyList;
    private MiscellaneousStudentBodyList(Context context) {
        super(context);
        //code to add councils
        MiscellaneousStudentBody body = new MiscellaneousStudentBody();
        body.setImage(R.mipmap.shutterbugs_cover);
        body.setLogo(R.mipmap.shutterbugs_logo);
        body.setName(mContext.getString(R.string.shutterbugs));
        body.setDescription(mContext.getString(R.string.shutterbugs_hello));
        add(body);
        body = new MiscellaneousStudentBody();
        body.setImage(R.mipmap.bloombox_cover);
        body.setLogo(R.mipmap.bloombox_logo);
        body.setName(mContext.getString(R.string.bloombox));
        body.setDescription(mContext.getString(R.string.bloombox_hello));
        add(body);
        body = new MiscellaneousStudentBody();
        body.setImage(R.mipmap.alumni_cell_cover);
        body.setLogo(R.mipmap.alumni_cell_logo);
        body.setName(mContext.getString(R.string.alumni_cell));
        body.setDescription(mContext.getString(R.string.alumni_cell_hello));
        add(body);
        body = new MiscellaneousStudentBody();
        body.setName(mContext.getString(R.string.push));
        body.setDescription(mContext.getString(R.string.push_hello));
        add(body);
        body = new MiscellaneousStudentBody();
        body.setName(mContext.getString(R.string.kshitij));
        body.setDescription(mContext.getString(R.string.kshitij_hello));
        add(body);
        body = new MiscellaneousStudentBody();
        body.setName(mContext.getString(R.string.parvaah));
        body.setDescription(mContext.getString(R.string.parvaah_hello));
        add(body);
    }

    public static MiscellaneousStudentBodyList getInstance(Context context){
        if(mMiscellaneousStudentBodyList == null){
            mMiscellaneousStudentBodyList = new MiscellaneousStudentBodyList(context);
        }
        return mMiscellaneousStudentBodyList;
    }
    @Override
    public int getCategory() {
        return CATEGORY_MISCELLANEOUS;
    }
}
