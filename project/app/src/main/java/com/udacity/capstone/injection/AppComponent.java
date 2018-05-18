package com.udacity.capstone.injection;

import com.udacity.capstone.BaseActivity;
import com.udacity.capstone.BaseFragment;
import com.udacity.capstone.CapstoneApplication;
import com.udacity.capstone.modules.auth.LoginActivity;
import com.udacity.capstone.modules.detail.DetailFragment;
import com.udacity.capstone.modules.detail.DetailViewModel;
import com.udacity.capstone.modules.main.MainActivity;
import com.udacity.capstone.modules.main.MainViewModel;
import com.udacity.capstone.modules.onboarding.OnboardingActivity;
import com.udacity.capstone.modules.recyclerView.ItemAdapter;
import com.udacity.capstone.modules.recyclerView.RecyclerViewFragment;
import com.udacity.capstone.modules.recyclerView.RecyclerViewModel;
import com.udacity.capstone.modules.settings.SettingsActivity;

import javax.inject.Singleton;

import dagger.Component;



@SuppressWarnings("unused")
@Singleton
@Component(modules={AppModule.class, NetModule.class})
public interface AppComponent {

    void inject(CapstoneApplication application);

    void inject(LoginActivity loginActivity);

    void inject(MainActivity mainActivity);

    void inject(MainViewModel mainViewModel);

    void inject(DetailViewModel detailViewModel);

    void inject(RecyclerViewFragment recyclerViewFragment);

    void inject(ItemAdapter itemAdapter);

    void inject(DetailFragment detailFragment);

    void inject(BaseActivity baseActivity);

    void inject(BaseFragment baseFragment);

    void inject(RecyclerViewModel recyclerViewModel);

    void inject(SettingsActivity settingsActivity);

    void inject(OnboardingActivity onboardingActivity);
}