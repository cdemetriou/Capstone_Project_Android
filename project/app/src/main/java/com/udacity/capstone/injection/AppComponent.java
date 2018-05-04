package com.udacity.capstone.injection;

import com.udacity.capstone.modules.auth.LoginActivity;
import com.udacity.capstone.data.remote.MarvelService;
import com.udacity.capstone.data.remote.Repository;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by christosdemetriou on 18/04/2018.
 */

@Singleton
@Component(modules={AppModule.class, NetModule.class})
public interface AppComponent {

    MarvelService dataService();
    Repository repository();

    void inject(LoginActivity loginActivity);

}