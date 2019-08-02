package com.test.adminconsole.FCM;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;


public class FcmInstanceIdService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {

        //String recent_token = FirebaseInstanceId.getInstance().getToken();
        //Log.w("TOKEN",recent_token);


        FirebaseMessaging.getInstance().subscribeToTopic("admin");

    }
}
