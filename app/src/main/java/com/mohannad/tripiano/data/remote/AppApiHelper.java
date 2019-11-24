package com.mohannad.tripiano.data.remote;

import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.mohannad.tripiano.data.model.RequestCallback;
import com.mohannad.tripiano.ui.login.LoginActivity;

public class AppApiHelper implements ApiHelper {

    @Override
    public void login(String email, String password, final RequestCallback requestCallback) {
        Backendless.UserService.login(email,password,
                new AsyncCallback<BackendlessUser>() {
                    @Override
                    public void handleResponse(BackendlessUser response) {
//                        Toast.makeText(LoginActivity.this, "email"+response.getObjectId(),
//                                Toast.LENGTH_SHORT).show();
////                        loginSuccess();
//                        dismissLoading();
                        requestCallback.success(response);
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
//                        Toast.makeText(LoginActivity.this,  fault.getMessage(),
//                                Toast.LENGTH_SHORT).show();
//                        dismissLoading();
                        requestCallback.error(fault);
                    }
                }, true);
    }
}
