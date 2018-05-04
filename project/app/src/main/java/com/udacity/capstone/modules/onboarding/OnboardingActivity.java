package com.udacity.capstone.modules.onboarding;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.graphics.Palette;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.udacity.capstone.BaseActivity;
import com.udacity.capstone.R;
import com.udacity.capstone.databinding.ActivityOnboardingBinding;
import com.udacity.capstone.databinding.FragmentPagerBinding;
import com.udacity.capstone.modules.auth.LoginActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by christosdemetriou on 24/04/2018.
 */


public class OnboardingActivity extends BaseActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;
    ActivityOnboardingBinding binding;
    List<ImageView> indicators = new ArrayList<>();

    int lastLeftValue = 0;


    int page = 0;   //  to track page position

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_onboarding);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        binding.pager.setAdapter(mSectionsPagerAdapter);
        binding.pager.setCurrentItem(page);

        indicators.add(binding.page1Indicator);
        indicators.add(binding.page2Indicator);
        indicators.add(binding.page3Indicator);
        updateIndicators(page);

        binding.pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                page = position;
                updateIndicators(page);
                binding.next.setText(page == 2 ? "Finish" : "Next");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (page != 2){
                    page++;
                    binding.pager.setCurrentItem(page, true);
                }
                else {
                    startActivity(new Intent(OnboardingActivity.this, LoginActivity.class));
                }

            }
        });

        binding.previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (page > 0) {
                    page--;
                    binding.pager.setCurrentItem(page, true);
                }

            }
        });



    }

    void updateIndicators(int position) {
        for (int i = 0; i < indicators.size(); i++) {
            indicators.get(i).setBackgroundResource(i == position ? R.drawable.pager_item_selected : R.drawable.pager_item_unselected);
        }
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        FragmentPagerBinding binding;
        int[] bgs = new int[]{R.drawable.ironman, R.drawable.hellboy, R.drawable.margeto};

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pager, container, false);

            int page = getArguments().getInt(ARG_SECTION_NUMBER);
            binding.title.setText("Title");
            binding.description.setText("Description");

            Bitmap myBitmap = BitmapFactory.decodeResource(getResources(), bgs[getArguments().getInt(ARG_SECTION_NUMBER) - 1]);
            if (myBitmap != null && !myBitmap.isRecycled()) {
                Palette.from(myBitmap).generate(paletteListener);
            }
            binding.sectionImg.setBackgroundResource(bgs[getArguments().getInt(ARG_SECTION_NUMBER) - 1]);

            return binding.getRoot();
        }

        Palette.PaletteAsyncListener paletteListener = new Palette.PaletteAsyncListener() {
            public void onGenerated(Palette palette) {
                int def = 0x000000;
                int vibrant = palette.getVibrantColor(def);

                binding.root.setBackgroundColor(vibrant);
            }
        };

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {


        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position + 1);

        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }

    }


}
