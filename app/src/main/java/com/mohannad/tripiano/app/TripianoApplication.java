package com.mohannad.tripiano.app;

import android.app.Application;
import android.widget.Toast;

import com.backendless.Backendless;
import com.google.android.material.snackbar.Snackbar;
import com.mohannad.tripiano.R;
import com.mohannad.tripiano.utils.NetworkUtils;

public class TripianoApplication  extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (!NetworkUtils.isNetworkConnected(this)) {
            Toast.makeText(this,"please make sure that you are connected to interne" , Toast.LENGTH_LONG).show();
            return ;
        }
        Backendless.setUrl(getString(R.string.backendless_SERVER_URL));
        Backendless.initApp(this,
                getString(R.string.backendless_APPLICATION_ID),
                getString(R.string.backendless_API_KEY));
    }
}
