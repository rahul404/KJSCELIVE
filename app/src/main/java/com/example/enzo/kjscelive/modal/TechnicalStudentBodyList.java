package com.example.enzo.kjscelive.modal;

import android.content.Context;

import com.example.enzo.kjscelive.R;

/**
 * Created by enzo on 12/11/2017.
 */

public class TechnicalStudentBodyList extends StudentBodyList<TechnicalStudentBody>{
    private static TechnicalStudentBodyList mTechnicalStudentBodyList;
    private TechnicalStudentBodyList(Context context) {
        super(context);
        //adding new bodies
        TechnicalStudentBody body;
        body = new TechnicalStudentBody();
        body.setImage(R.mipmap.csi_cover);
        body.setLogo(R.mipmap.csi_logo);
        body.setName(mContext.getString(R.string.csi));
        body.setDescription(mContext.getString(R.string.csi_hello));
        add(body);
        body = new TechnicalStudentBody();
        body.setImage(R.mipmap.codecell_cover);
        body.setLogo(R.mipmap.codecell_logo);
        body.setName(mContext.getString(R.string.codecell));
        body.setDescription(mContext.getString(R.string.codecell_hello));
        add(body);
        body = new TechnicalStudentBody();
        body.setName(mContext.getString(R.string.ieee));
        body.setDescription(mContext.getString(R.string.ieee_hello));
        add(body);
        body = new TechnicalStudentBody();
        body.setImage(R.mipmap.eesa_cover);
        body.setLogo(R.mipmap.eesa_logo);
        body.setName(mContext.getString(R.string.eesa));
        body.setDescription(mContext.getString(R.string.eesa_hello));
        add(body);
        body = new TechnicalStudentBody();
        body.setName(mContext.getString(R.string.mesa));
        body.setDescription(mContext.getString(R.string.mesa_hello));
        add(body);
        body = new TechnicalStudentBody();
        body.setLogo(R.mipmap.ishrae_logo);
        body.setImage(R.mipmap.ishrae_cover);
        body.setName(mContext.getString(R.string.ishrae));
        body.setDescription(mContext.getString(R.string.ishrae_hello));
        add(body);
        body = new TechnicalStudentBody();
        body.setImage(R.mipmap.iete_cover);
        body.setLogo(R.mipmap.iete_logo);
        body.setName(mContext.getString(R.string.iete));
        body.setDescription(mContext.getString(R.string.iete_hello));
        add(body);
        body = new TechnicalStudentBody();
        body.setName(mContext.getString(R.string.iste));
        body.setDescription(mContext.getString(R.string.iste_hello));
        add(body);
        body = new TechnicalStudentBody();
        body.setName(mContext.getString(R.string.sahas));
        body.setDescription(mContext.getString(R.string.sahas_hello));
        add(body);
        body = new TechnicalStudentBody();
        body.setImage(R.mipmap.emfinity_cover);
        body.setLogo(R.mipmap.emfinity_logo);
        body.setName(mContext.getString(R.string.emfinity));
        body.setDescription(mContext.getString(R.string.emfinity_hello));
        add(body);
        body = new TechnicalStudentBody();
        body.setName(mContext.getString(R.string.orion));
        body.setDescription(mContext.getString(R.string.orion_hello));
        add(body);
        body = new TechnicalStudentBody();
        body.setName(mContext.getString(R.string.redshift));
        body.setDescription(mContext.getString(R.string.redshift_hello));
        add(body);
        body = new TechnicalStudentBody();
        body.setLogo(R.mipmap.eta_logo);
        body.setImage(R.mipmap.eta_cover);
        body.setName(mContext.getString(R.string.eta));
        body.setDescription(mContext.getString(R.string.eta_hello));
        add(body);
        body = new TechnicalStudentBody();
        body.setName(mContext.getString(R.string.onyx));
        body.setDescription(mContext.getString(R.string.onyx_hello));
        add(body);
        body = new TechnicalStudentBody();
        body.setName(mContext.getString(R.string.robocon));
        body.setDescription(mContext.getString(R.string.robocon_hello));
        add(body);
        body = new TechnicalStudentBody();
        body.setName(mContext.getString(R.string.mindbenders));
        body.setDescription(mContext.getString(R.string.mindbenders_hello));
        add(body);
    }

    public static TechnicalStudentBodyList getInstance(Context context){
        if(mTechnicalStudentBodyList == null){
            mTechnicalStudentBodyList = new TechnicalStudentBodyList(context);
        }
        return mTechnicalStudentBodyList;
    }
    @Override
    public int getCategory() {
        return CATEGORY_TECHNICAL;
    }
}
