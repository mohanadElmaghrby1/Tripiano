package com.mohannad.tripiano.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.mohannad.tripiano.R;
import com.mohannad.tripiano.databinding.ActivityLoginBinding;
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
        binding.registerbtn.setText("new only");

    }

    public void login(View view) {
        if (!NetworkUtils.isNetworkConnected(this)) {
            Snackbar.make(findViewById(R.id.scrollView),"please make sure that you are connected to intenrnet", Snackbar.LENGTH_SHORT).show();
            return ;
        }

        showLoading();


    }


    private void showLoading() {
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    private void dismissLoading() {
        progressDialog.dismiss();
    }
}
