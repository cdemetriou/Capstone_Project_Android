package com.udacity.capstone.modules.detail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.MenuItem;

import com.udacity.capstone.BaseActivity;
import com.udacity.capstone.R;
import com.udacity.capstone.data.model.Item;
import com.udacity.capstone.databinding.ActivityCharacterDetailBinding;
import com.udacity.capstone.modules.RecyclerViewFragment;

import org.parceler.Parcels;

/**
 * Created by christosdemetriou on 04/05/2018.
 */

public class CharacterDetailActivity extends BaseActivity {

    ActivityCharacterDetailBinding binding;
    public static Item item;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = DataBindingUtil.setContentView(this, R.layout.activity_character_detail);

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Character");

        item = Parcels.unwrap(getIntent().getExtras().getParcelable("item"));

        FragmentPagerAdapter adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
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

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 2;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
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
                    return DetailFragment.newInstance(item);
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return RecyclerViewFragment.newInstance(1, "Page # 2", null);
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return "Details";
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return "Comics";
                default:
                    return null;
            }
        }

    }
}
