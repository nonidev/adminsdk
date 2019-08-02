package com.adminsdk.listeners;

public interface OnRequestListener {
    void onInit();
    void onError(int responseCode,String message);
    void onSuccess(int responseCode,String message);
}
