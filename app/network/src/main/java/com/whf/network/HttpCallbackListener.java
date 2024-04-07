package com.whf.network;

public interface HttpCallbackListener {

    void onFinish(String response);

    void onError(Exception e);
}
