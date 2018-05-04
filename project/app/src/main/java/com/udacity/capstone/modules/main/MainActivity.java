package com.udacity.capstone.modules.main;

import android.arch.lifecycle.Observer;
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

import timber.log.Timber;

public class MainActivity extends BaseActivity {

    ActivityMainBinding binding;
    MainViewModel viewModel;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setupActivity();
        viewModel = new MainViewModel(this);

        viewModel.getCharacterList().observe(this, myObserver);
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
            Timber.e("resutls");
            displayItems(list);
        }
    };

    private final NavigationView.OnNavigationItemSelectedListener navigationListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // Handle navigation view item clicks here.
            int id = item.getItemId();
            switch (id){
                case  R.id.nav_characters:
                    viewModel.getCharacterList().observe(MainActivity.this, myObserver);

                    break;
                case R.id.nav_comics:
                    viewModel.getComicsList().observe(MainActivity.this, myObserver);
                    break;
            }

            binding.drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
    };

    private final MaterialSearchView.OnQueryTextListener QueryTextListener = new MaterialSearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            //Do some magic
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            //Do some magic
            return false;
        }
    };

    private final MaterialSearchView.SearchViewListener SearchViewListener = new MaterialSearchView.SearchViewListener() {
        @Override
        public void onSearchViewShown() {
            //Do some magic
        }

        @Override
        public void onSearchViewClosed() {
            //Do some magic
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
