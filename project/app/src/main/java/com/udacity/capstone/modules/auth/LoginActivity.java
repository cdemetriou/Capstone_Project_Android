package com.udacity.capstone.modules.auth;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.auth.AuthUI;
import com.udacity.capstone.BaseActivity;
import com.udacity.capstone.CapstoneApplication;
import com.udacity.capstone.R;
import com.udacity.capstone.modules.main.MainActivity;

import java.util.Arrays;
import java.util.List;

import static com.udacity.capstone.data.Constants.LOGIN_SCREEN_NAME;
import static com.udacity.capstone.data.Constants.RC_SIGN_IN;

public class LoginActivity extends BaseActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.loginTheme);

        CapstoneApplication.getApplicationComponent(this).inject(this);

        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build());

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setTheme(R.style.loginTheme)
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    public String getScreenNameForAnalytics() {
        return LOGIN_SCREEN_NAME;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN && resultCode == RESULT_OK) {

                startActivity(new Intent(this, MainActivity.class));
            }
            else {

                new AlertDialog.Builder(this)
                        .setTitle(R.string.error_login_title)
                        .setMessage(R.string.error_login_message)
                        .setCancelable(true)
                        .setNegativeButton(R.string.error_login_action, null)
                        .show();
            }
        }
    }

