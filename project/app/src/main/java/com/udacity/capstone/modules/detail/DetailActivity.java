package com.udacity.capstone.modules.detail;

import android.arch.lifecycle.Observer;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.MenuItem;

import com.udacity.capstone.BaseActivity;
import com.udacity.capstone.R;
import com.udacity.capstone.data.model.Item;
import com.udacity.capstone.data.model.ItemList;
import com.udacity.capstone.databinding.ActivityCharacterDetailBinding;
import com.udacity.capstone.modules.RecyclerViewFragment;
import com.udacity.capstone.modules.main.MainViewModel;

import org.parceler.Parcels;

import java.util.ArrayList;

import timber.log.Timber;

import static com.udacity.capstone.data.model.ItemList.Type.isCharacter;
import static com.udacity.capstone.data.model.ItemList.Type.isComic;

/**
 * Created by christosdemetriou on 04/05/2018.
 */

public class DetailActivity extends BaseActivity {

    ActivityCharacterDetailBinding binding;
    MyPagerAdapter adapterViewPager;
    private DetailViewModel viewModel;
    public static Item item;
    public static int type;
    ItemList items;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_character_detail);


        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        item = Parcels.unwrap(getIntent().getExtras().getParcelable("item"));
        type = getIntent().getExtras().getInt("type");


        viewModel = new DetailViewModel(this, item.getId(), type);

        viewModel.getList().observe(this, myObserver);

        switch (type){
            case isCharacter:
                //viewModel.getComicsList().observe(this, myObserver);
                getSupportActionBar().setTitle("Character");
                Timber.e("character get comics");
                break;
            case isComic:
                getSupportActionBar().setTitle("Comic");
                Timber.e("comic get characters");
                break;
        }
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        binding.vpPager.setAdapter(adapterViewPager);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    final Observer<ItemList> myObserver = new Observer<ItemList>() {
        @Override
        public void onChanged(@Nullable final ItemList list) {
            items = list;
            adapterViewPager.setList(items);
            adapterViewPager.notifyDataSetChanged();
        }
    };


    public static class MyPagerAdapter extends FragmentStatePagerAdapter {
        private static int NUM_ITEMS = 2;
        private ItemList adapterList;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public void setList(ItemList list){
            adapterList = list;
        }
        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return DetailFragment.newInstance(item, type);
                case 1: // Fragment # 0 - List of comics for characters and list of characters for comics
                    return RecyclerViewFragment.newInstance(1, "Page # 2", adapterList);
                default:
                    return null;
            }
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return "Details";
                case 1: // Fragment # 0 - List of comics for characters and list of characters for comics
                    if (type == isCharacter) return "Comics";
                    else if (type == isComic) return "Characters";
                default:
                    return null;
            }
        }

    }
}
