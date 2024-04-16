package com.whf.advanced.util;

public interface HttpCallbackListener {


        void onFinish(String response);

        void onError(Exception e);
}
