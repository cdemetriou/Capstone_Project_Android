package com.udacity.capstone.modules.detail;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.MenuItem;

import com.udacity.capstone.BaseActivity;
import com.udacity.capstone.CapstoneApplication;
import com.udacity.capstone.R;
import com.udacity.capstone.data.model.Item;
import com.udacity.capstone.data.model.ItemList;
import com.udacity.capstone.databinding.ActivityCharacterDetailBinding;
import com.udacity.capstone.modules.main.MainActivity;
import com.udacity.capstone.modules.recyclerView.RecyclerViewFragment;

import org.parceler.Parcels;


import static com.udacity.capstone.data.Constants.DETAIL_SCREEN_NAME;

import static com.udacity.capstone.data.Constants.EXTRAS_ITEM;
import static com.udacity.capstone.data.Constants.EXTRAS_TYPE;
import static com.udacity.capstone.data.model.ItemList.Type.isCharacter;
import static com.udacity.capstone.data.model.ItemList.Type.isComic;
import static com.udacity.capstone.data.model.ItemList.Type.isEvent;
import static com.udacity.capstone.data.model.ItemList.Type.isSeries;


public class DetailActivity extends BaseActivity {

    private MyPagerAdapter adapterViewPager;

    private static Item item;

    private static int type;

    private static Context context;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CapstoneApplication.getApplicationComponent(this).inject(this);

        ActivityCharacterDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_character_detail);

        context = this;
        setSupportActionBar(binding.toolbar);

        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent().getExtras() != null) {
            item = Parcels.unwrap(getIntent().getExtras().getParcelable(EXTRAS_ITEM));
            type = getIntent().getExtras().getInt(EXTRAS_TYPE);
        }

        DetailViewModel viewModel = new DetailViewModel(this, item.getId(), type);

        viewModel.getFirstList().observe(this, firstObserver);
        viewModel.getSecondList().observe(this, secondObserver);
        viewModel.getThirdList().observe(this, thirdObserver);

        switch (type){
            case isCharacter:
                getSupportActionBar().setTitle(R.string.character);
                break;
            case isComic:
                getSupportActionBar().setTitle(R.string.comic);
                break;
            case isSeries:
                getSupportActionBar().setTitle(R.string.series);
                break;
            case isEvent:
                getSupportActionBar().setTitle(R.string.event);
                break;
        }

        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        binding.vpPager.setAdapter(adapterViewPager);
    }

    @Override
    public String getScreenNameForAnalytics() {
        return DETAIL_SCREEN_NAME;
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

    private final Observer<ItemList> firstObserver = new Observer<ItemList>() {
        @Override
        public void onChanged(@Nullable final ItemList list) {
            adapterViewPager.setFirstList(list);
            adapterViewPager.notifyDataSetChanged();
        }
    };

    private final Observer<ItemList> secondObserver = new Observer<ItemList>() {
        @Override
        public void onChanged(@Nullable final ItemList list) {
            adapterViewPager.setSecondList(list);
            adapterViewPager.notifyDataSetChanged();
        }
    };

    private final Observer<ItemList> thirdObserver = new Observer<ItemList>() {
        @Override
        public void onChanged(@Nullable final ItemList list) {
            adapterViewPager.setThirdList(list);
            adapterViewPager.notifyDataSetChanged();
        }
    };

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @SuppressWarnings("CanBeFinal")
    public static class MyPagerAdapter extends FragmentStatePagerAdapter {

        private static int NUM_ITEMS = 4;

        private ItemList firstList;

        private ItemList secondList;

        private ItemList thirdList;


        MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        void setFirstList(ItemList list){firstList = list;}

        void setSecondList(ItemList list){
            secondList = list;
        }

        void setThirdList(ItemList list){
            thirdList = list;
        }


        @Override
        public int getCount() {
            return NUM_ITEMS;
        }


        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return DetailFragment.newInstance(item);
                case 1:
                    return RecyclerViewFragment.newInstance(false, null, firstList);
                case 2:
                    return RecyclerViewFragment.newInstance(false, null, secondList);
                case 3:
                    return RecyclerViewFragment.newInstance(false, null, thirdList);
                default:
                    return null;
            }
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:

                    return context.getResources().getString(R.string.details);
                case 1:

                    switch (type){
                        case isCharacter:
                            return context.getResources().getString(R.string.comics);
                        case isComic:
                        case isSeries:
                        case isEvent:
                            return context.getResources().getString(R.string.characters);
                        default:
                            return null;
                    }
                case 2:

                    switch (type){
                        case isCharacter:
                        case isComic:
                            return context.getResources().getString(R.string.series);
                        case isSeries:
                        case isEvent:
                            return context.getResources().getString(R.string.comics);
                        default:
                            return null;
                    }
                case 3:

                    switch (type){
                        case isCharacter:
                        case isComic:
                        case isSeries:
                            return context.getResources().getString(R.string.events);
                        case isEvent:
                            return context.getResources().getString(R.string.series);
                        default:
                            return null;
                    }
                default:

                    return null;


            }
        }

    }
}
