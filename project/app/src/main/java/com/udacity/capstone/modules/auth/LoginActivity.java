package com.udacity.capstone.modules.auth;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.udacity.capstone.BaseActivity;
import com.udacity.capstone.CapstoneApplication;
import com.udacity.capstone.modules.MainActivity;
import com.udacity.capstone.R;
import com.udacity.capstone.data.remote.Repository;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class LoginActivity extends BaseActivity {

    private static final int RC_SIGN_IN = 123;

    @Inject
    Repository repository;

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                startActivity(new Intent(this, MainActivity.class));


            } else {
                // Sign in failed, check response for error code
                // ...
            }
        }
    }
}
