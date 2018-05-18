package com.udacity.capstone.modules.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.suke.widget.SwitchButton;
import com.udacity.capstone.BaseActivity;
import com.udacity.capstone.CapstoneApplication;
import com.udacity.capstone.R;
import com.udacity.capstone.databinding.ActivitySettingsBinding;
import com.udacity.capstone.modules.auth.LoginActivity;

import javax.inject.Inject;

import static com.udacity.capstone.data.Constants.SETTINGS_SCREEN_NAME;



@SuppressWarnings("WeakerAccess")
public class SettingsActivity extends BaseActivity {

    private ActivitySettingsBinding binding;

    @Inject
    FirebaseAuth firebaseAuth;
    @Inject
    SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CapstoneApplication.getApplicationComponent(this).inject(this);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings);

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        seUpSharedPreferences();
        binding.logoutButton.setOnClickListener(logOutListener);
    }

    private void seUpSharedPreferences() {
        binding.analyticsSwitch.setChecked(sharedPreferences.getBoolean(getString(R.string.pref_key_analytics_enabled), true));
        binding.analyticsSwitch.setOnCheckedChangeListener(onChangeListener);

    }

    private final com.suke.widget.SwitchButton.OnCheckedChangeListener onChangeListener = new SwitchButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(SwitchButton view, boolean isChecked) {
            sharedPreferences.edit().putBoolean(getString(R.string.pref_key_analytics_enabled), isChecked).apply();
        }
    };

    private final Button.OnClickListener logOutListener = (view) -> {
        firebaseAuth.signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    };

    @Override
    public String getScreenNameForAnalytics() {
        return SETTINGS_SCREEN_NAME;
    }
}
