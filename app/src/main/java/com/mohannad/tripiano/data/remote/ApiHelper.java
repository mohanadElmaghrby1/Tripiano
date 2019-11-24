package com.mohannad.tripiano.data.remote;

import com.mohannad.tripiano.data.model.RequestCallback;

import javax.security.auth.callback.Callback;

public interface ApiHelper {
    void login(String email , String password , RequestCallback requestCallback);
}
