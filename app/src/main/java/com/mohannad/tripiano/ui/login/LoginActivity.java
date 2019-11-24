package com.mohannad.tripiano.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.google.android.material.snackbar.Snackbar;
import com.mohannad.tripiano.R;
import com.mohannad.tripiano.databinding.ActivityLoginBinding;
import com.mohannad.tripiano.ui.registeration.RegisterActivity;
import com.mohannad.tripiano.ui.trips.TripActivity;
import com.mohannad.tripiano.utils.NetworkUtils;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("loading");

    }

    public void login(View view) {
        if (!NetworkUtils.isNetworkConnected(this)) {
            Snackbar.make(findViewById(R.id.scrollView),"please make sure that you are connected to intenrnet", Snackbar.LENGTH_SHORT).show();
            return ;
        }
        onLoginButtonClicked();
    }

    private void onLoginButtonClicked() {
        showLoading();
        Backendless.UserService.login(binding.etPhoneNumber.getText().toString(),
                binding.etPass.getText().toString(),
                new AsyncCallback<BackendlessUser>() {
                    @Override
                    public void handleResponse(BackendlessUser response) {
                        Toast.makeText(LoginActivity.this, "email"+response.getObjectId(),
                                Toast.LENGTH_SHORT).show();
//                        loginSuccess();
                        dismissLoading();
                        openHome();
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Toast.makeText(LoginActivity.this,  fault.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        dismissLoading();
                    }
                }, true);
    }


    private void showLoading() {
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    private void dismissLoading() {
        progressDialog.dismiss();
    }

    public void register(View view) {
        Intent intent =new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void openHome() {
        Intent intent =new Intent(this, TripActivity.class);
        startActivity(intent);
        finish();
    }
}
