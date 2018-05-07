package com.udacity.capstone.modules.main;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.udacity.capstone.BaseActivity;
import com.udacity.capstone.R;
import com.udacity.capstone.data.model.Item;
import com.udacity.capstone.data.model.ItemList;
import com.udacity.capstone.databinding.ActivityMainBinding;
import com.udacity.capstone.modules.RecyclerViewFragment;

import java.util.ArrayList;
import java.util.List;

import icepick.State;
import timber.log.Timber;

public class MainActivity extends BaseActivity {

    @State
    int navigationOption = R.id.nav_characters;

    ActivityMainBinding binding;
    MainViewModel viewModel;
    ItemList characters;
    ItemList comics;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    String searchTerm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setupActivity();
        viewModel = new MainViewModel(this);

        viewModel.getCharacterList().observe(this, myObserver);
        viewModel.getComicsList().observe(this, myObserver);
    }

    private void setupActivity() {
        setSupportActionBar(binding.appBarMain.toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout,
                binding.appBarMain.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();



        TextView nameView = binding.navView.getHeaderView(0).findViewById(R.id.name);
        TextView emailView = binding.navView.getHeaderView(0).findViewById(R.id.email);
        nameView.setText(user != null ? user.getDisplayName() : null);
        emailView.setText(user != null ? user.getEmail() : null);

        binding.navView.setNavigationItemSelectedListener(navigationListener);
        binding.appBarMain.searchView.setOnQueryTextListener(QueryTextListener);
        binding.appBarMain.searchView.setOnSearchViewListener(SearchViewListener);
    }


    final Observer<ItemList> myObserver = new Observer<ItemList>() {
        @Override
        public void onChanged(@Nullable final ItemList list) {
            if (searchTerm != null) {
                displayItems(list);
                return;
            }
            if (list.isCharacter) characters = list;
            else comics = list;

            if (navigationOption != -1) {
                switch (navigationOption) {
                    case R.id.nav_characters:
                        displayItems(characters);
                        break;
                    case R.id.nav_comics:
                        displayItems(comics);
                        break;
                }
            }
            else displayItems(characters);
        }
    };

    private final NavigationView.OnNavigationItemSelectedListener navigationListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            int id = item.getItemId();

            switch (id){
                case  R.id.nav_characters:
                    displayItems(characters);
                    break;
                case R.id.nav_comics:
                    displayItems(comics);
                    break;
            }

            navigationOption = id;
            binding.drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
    };

    private final MaterialSearchView.OnQueryTextListener QueryTextListener = new MaterialSearchView.OnQueryTextListener() {
        private int count = 0;
        @Override
        public boolean onQueryTextSubmit(String query) {
            switch (navigationOption){
                case  R.id.nav_characters:
                    //Do some magic
                    searchTerm = query;
                    viewModel.getCharacterList().observe(MainActivity.this, myObserver);
                    displayItems(null);
                    viewModel.searchCharacters(query);
                    break;

                case R.id.nav_comics:
                    //Do some magic
                    searchTerm = query;
                    viewModel.getComicsList().observe(MainActivity.this, myObserver);
                    displayItems(null);
                    viewModel.searchComics(query);
                    break;
            }

            if (count < 1) {
                count++;
                this.onQueryTextSubmit(query);
                return true;
            }
            count = 0;
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(binding.getRoot().getWindowToken(), 0);

            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    };

    private final MaterialSearchView.SearchViewListener SearchViewListener = new MaterialSearchView.SearchViewListener() {
        @Override
        public void onSearchViewShown() {
        }

        @Override
        public void onSearchViewClosed() {
            searchTerm = null;
        }
    };


    public void displayItems(ItemList list){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment charactersFragment = RecyclerViewFragment.newInstance(0, null, list);
        transaction.replace(R.id.frame_layout, charactersFragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        binding.appBarMain.searchView.setMenuItem(item);


        return true;
    }
}
