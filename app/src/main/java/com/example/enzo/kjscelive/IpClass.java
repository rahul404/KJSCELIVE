package com.example.enzo.kjscelive;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by enzo on 8/8/2017.
 */

public class IpClass {
    private Context mContext;
    String mIp="rahulahuja404.000webhostapp.com";
    String mUrl="/android/kjscelive/feed.php?key=legendary&card_id=";
    String mEmail = "rahul.ahuja@somaiya.edu";

    public void setContext(Context context){
        mContext = context;
    }
    public void setEmail(String email) {
        mEmail = email;
    }

    public String getEmail() {

        //return mEmail;
        return mEmail;
    }


    public static IpClass mIpClass=null;
    private IpClass(){

    }
    public static IpClass getInstance(){
        if(mIpClass==null){
            mIpClass=new IpClass();
        }
        return mIpClass;
    }
    public String getIp() {
        return mIp;
    }

    public String getUrl() {
        return "http://"+mIp+mUrl;
    }

    public void setIp(String ip) {
        mIp = ip;
    }

    public void setUrl(String url) {
        mUrl = url;
    }
}
