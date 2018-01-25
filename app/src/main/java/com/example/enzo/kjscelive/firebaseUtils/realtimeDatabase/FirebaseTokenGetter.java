package com.example.enzo.kjscelive.firebaseUtils.realtimeDatabase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by enzo on 12/9/2017.
 */

public class FirebaseTokenGetter extends FirebaseInstanceIdService{
    public void onTokenRefresh(){

        String refreshToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("FPN","Refreshed token ="+refreshToken);

    }
}
