package com.udacity.capstone.modules.main;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
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
import com.udacity.capstone.CapstoneApplication;
import com.udacity.capstone.R;
import com.udacity.capstone.firebase.FirebaseAnalyticsManager;
import com.udacity.capstone.firebase.FirebaseDatabaseManager;
import com.udacity.capstone.data.model.Item;
import com.udacity.capstone.data.model.ItemList;
import com.udacity.capstone.databinding.ActivityMainBinding;
import com.udacity.capstone.modules.recyclerView.RecyclerViewFragment;
import com.udacity.capstone.modules.settings.SettingsActivity;
import com.udacity.capstone.widget.WidgetManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import icepick.State;

import static com.udacity.capstone.CapstoneApplication.getWidgetManager;
import static com.udacity.capstone.data.Constants.DETAIL_TITLE_CHARACTERS;
import static com.udacity.capstone.data.Constants.DETAIL_TITLE_COMICS;
import static com.udacity.capstone.data.Constants.DETAIL_TITLE_EVENTS;
import static com.udacity.capstone.data.Constants.DETAIL_TITLE_SERIES;
import static com.udacity.capstone.data.Constants.MAIN_SCREEN_NAME;
import static com.udacity.capstone.data.Constants.TITLE_MY_TEAM;
import static com.udacity.capstone.data.Constants.TITLE_SETTINGS;


@SuppressWarnings("WeakerAccess")
public class MainActivity extends BaseActivity implements FirebaseDatabaseManager.EventCallback {

    @State
    int navigationOption = R.id.nav_characters;

    @Inject
    FirebaseAuth auth;
    @Inject
    FirebaseDatabaseManager databaseManager;
    @Inject
    FirebaseAnalyticsManager analyticsManager;

    private ActivityMainBinding binding;

    private MainViewModel viewModel;

    private ItemList characters;
    private ItemList comics;
    private ItemList series;
    private ItemList events;
    private ItemList favorites;

    private String searchTerm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CapstoneApplication.getApplicationComponent(this).inject(this);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setupActivity();
        viewModel = new MainViewModel(this);

        viewModel.getCharacterList().observe(this, myObserver);
        viewModel.getComicsList().observe(this, myObserver);
        viewModel.getSeriesList().observe(this, myObserver);
        viewModel.getEventsList().observe(this, myObserver);


        databaseManager.setCallback(this);

        if (auth.getCurrentUser() != null){
            databaseManager.setUser();
            analyticsManager.logSetUser();
            databaseManager.getFavorites();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        databaseManager.setCallback(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        databaseManager.setCallback(null);
    }

    @Override
    public String getScreenNameForAnalytics() {
        return MAIN_SCREEN_NAME;
    }

    private void setupActivity() {
        setSupportActionBar(binding.appBarMain.toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle(DETAIL_TITLE_CHARACTERS);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout,
                binding.appBarMain.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        FirebaseUser user = auth.getCurrentUser();
        TextView nameView = binding.navView.getHeaderView(0).findViewById(R.id.name);
        TextView emailView = binding.navView.getHeaderView(0).findViewById(R.id.email);
        nameView.setText(user != null ? user.getDisplayName() : null);
        emailView.setText(user != null ? user.getEmail() : null);

        binding.navView.setNavigationItemSelectedListener(navigationListener);
        binding.appBarMain.searchView.setOnQueryTextListener(QueryTextListener);
        binding.appBarMain.searchView.setOnSearchViewListener(SearchViewListener);

        favorites = new ItemList();
        favorites.setType(ItemList.Type.isCharacter);
        favorites.setList(new ArrayList<>());
    }

    private final Observer<ItemList> myObserver = new Observer<ItemList>() {

        @Override
        public void onChanged(@Nullable final ItemList list) {

            if (searchTerm != null) {
                displayItems(list);
                return;
            }

            assert list != null;
            switch (list.getType()) {
                case ItemList.Type.isCharacter:
                    characters = list;
                    break;
                case ItemList.Type.isComic:
                    comics = list;
                    break;
                case ItemList.Type.isSeries:
                    series = list;
                    break;
                case ItemList.Type.isEvent:
                    events = list;
                    break;
            }

            if (navigationOption != -1) {
                switch (navigationOption) {
                    case R.id.nav_characters:
                        displayItems(characters);
                        break;
                    case R.id.nav_comics:
                        displayItems(comics);
                        break;
                    case R.id.nav_series:
                        displayItems(series);
                        break;
                    case R.id.nav_events:
                        displayItems(events);
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
            navigationOption = id;

            switch (id){
                case  R.id.nav_favorites:
                    if (getSupportActionBar() != null) getSupportActionBar().setTitle(TITLE_MY_TEAM);
                    displayItems(favorites);
                    break;
                case  R.id.nav_characters:
                    if (getSupportActionBar() != null) getSupportActionBar().setTitle(DETAIL_TITLE_CHARACTERS);
                    displayItems(characters);
                    break;
                case R.id.nav_comics:
                    if (getSupportActionBar() != null) getSupportActionBar().setTitle(DETAIL_TITLE_COMICS);
                    displayItems(comics);
                    break;
                case  R.id.nav_series:
                    if (getSupportActionBar() != null) getSupportActionBar().setTitle(DETAIL_TITLE_SERIES);
                    displayItems(series);
                    break;
                case R.id.nav_events:
                    if (getSupportActionBar() != null) getSupportActionBar().setTitle(DETAIL_TITLE_EVENTS);
                    displayItems(events);
                    break;
                case R.id.nav_settings:
                    if (getSupportActionBar() != null) getSupportActionBar().setTitle(TITLE_SETTINGS);
                    goToSettings();
                    break;
            }

            binding.drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
    };


    private final MaterialSearchView.OnQueryTextListener QueryTextListener = new MaterialSearchView.OnQueryTextListener() {

        private int count = 0;

        @Override
        public boolean onQueryTextSubmit(String query) {
            switch (navigationOption){
                case  R.id.nav_favorites:

                    searchTerm = query;
                    displayItems(null);
                    databaseManager.searchFavoritesByName(searchTerm);
                    break;

                case  R.id.nav_characters:

                    searchTerm = query;
                    viewModel.getCharacterList().observe(MainActivity.this, myObserver);
                    displayItems(null);
                    viewModel.searchCharacters(query);

                    if (count < 1) {
                        count++;
                        this.onQueryTextSubmit(query);
                        return true;
                    }
                    break;

                case R.id.nav_comics:

                    searchTerm = query;
                    viewModel.getComicsList().observe(MainActivity.this, myObserver);
                    displayItems(null);
                    viewModel.searchComics(query);

                    if (count < 1) {
                        count++;
                        this.onQueryTextSubmit(query);
                        return true;
                    }
                    break;

                case R.id.nav_series:

                    searchTerm = query;
                    viewModel.getSeriesList().observe(MainActivity.this, myObserver);
                    displayItems(null);
                    viewModel.searchSeries(query);

                    if (count < 1) {
                        count++;
                        this.onQueryTextSubmit(query);
                        return true;
                    }
                    break;

                case R.id.nav_events:

                    searchTerm = query;
                    viewModel.getEventsList().observe(MainActivity.this, myObserver);
                    displayItems(null);
                    viewModel.searchEvents(query);

                    if (count < 1) {
                        count++;
                        this.onQueryTextSubmit(query);
                        return true;
                    }
                    break;
            }

            count = 0;
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
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

    private void displayItems(ItemList itemList) {

        if (itemList != null) {
            List<Item> uniqueList = Item.unique(itemList.getList());
            itemList.setList(uniqueList);
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment charactersFragment = RecyclerViewFragment.newInstance(navigationOption == R.id.nav_favorites, searchTerm, itemList);
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



    @Override
    public void added(Item item) {
        favorites.getList().add(item);
        updateWidget();
        displayItems(characters);
    }

    @Override
    public void removed(Item item) {

        if (item != null) {
            for (Item it : favorites.getList()) {
                if (it.getId().equals(item.getId())) {
                    favorites.getList().remove(it);
                    updateWidget();
                    break;
                }
            }

            if (navigationOption != -1) {
                switch (navigationOption) {
                    case R.id.nav_characters:
                        displayItems(characters);
                        break;
                    case R.id.nav_favorites:
                        displayItems(favorites);
                        break;
                }
            }
            else displayItems(characters);
        }
    }

    @Override
    public void searchResult(ItemList list) {
        displayItems(list);
    }

    private void goToSettings() {
        startActivity(new Intent(this, SettingsActivity.class));
    }


    private void updateWidget() {

        WidgetManager widgetManager = getWidgetManager();
        widgetManager.setRecipe(favorites);
    }
}