package com.example.enzo.kjscelive;

/**
 * Created by enzo on 8/8/2017.
 */

public class IpClass {
    String mIp="rahulahuja404.000webhostapp.com";
    String mUrl="/android/kjscelive/feed.php?key=legendary&card_id=";
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
