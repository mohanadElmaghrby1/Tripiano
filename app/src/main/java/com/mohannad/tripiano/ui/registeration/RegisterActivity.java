package com.mohannad.tripiano.ui.registeration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.mohannad.tripiano.R;
import com.mohannad.tripiano.databinding.ActivityLoginBinding;
import com.mohannad.tripiano.databinding.ActivityRegisterationBinding;
import com.mohannad.tripiano.ui.login.LoginActivity;
import com.mohannad.tripiano.ui.trips.TripActivity;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_registeration);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registeration);

    }

    public void openHome() {
//        if (view==null){
            Intent intent =new Intent(this, TripActivity.class);
            startActivity(intent);
//        }else
//        onRegisterButtonClicked();
    }


    public void onRegisterButtonClicked(View view) {
        if (binding.etEmail.getText().toString().equals("")) {
            binding.etEmail.setError("please provide correct email");
            return;
        }
        if (binding.etPass.getText().toString().equals("")) {
            binding.etPass.setError("please provide correct pass");
            return;
        }

        BackendlessUser newUser = new BackendlessUser();
        newUser.setEmail(binding.etEmail.getText().toString());
        newUser.setProperty("name", binding.etUserName.getText().toString());
        newUser.setPassword(binding.etPass.getText().toString());

        Backendless.UserService.register(newUser, new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser response) {
                Toast.makeText(RegisterActivity.this,response.getEmail(),
                        Toast.LENGTH_SHORT).show();
                openHome();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(RegisterActivity.this, fault.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
