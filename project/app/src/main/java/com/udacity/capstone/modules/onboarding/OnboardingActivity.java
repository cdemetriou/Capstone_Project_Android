package com.udacity.capstone.modules.onboarding;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.udacity.capstone.CapstoneApplication;
import com.udacity.capstone.R;
import com.udacity.capstone.databinding.ActivityOnboardingBinding;
import com.udacity.capstone.databinding.FragmentPagerBinding;
import com.udacity.capstone.modules.auth.LoginActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.udacity.capstone.data.Constants.ARG_SECTION_NUMBER;
import static com.udacity.capstone.data.Constants.ONBOARDING_FINISH;
import static com.udacity.capstone.data.Constants.ONBOARDING_NEXT;
import static com.udacity.capstone.data.Constants.PREFERENCES_ONBOARDING;





@SuppressWarnings({"WeakerAccess", "CanBeFinal"})
public class OnboardingActivity extends AppCompatActivity {


    @Inject
    SharedPreferences sharedPreferences;

    private ActivityOnboardingBinding binding;

    private List<ImageView> indicators = new ArrayList<>();

    private int page = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CapstoneApplication.getApplicationComponent(this).inject(this);

        if (sharedPreferences.getBoolean(PREFERENCES_ONBOARDING, false)){
            startActivity(new Intent(OnboardingActivity.this, LoginActivity.class));
            finish();
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_onboarding);

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        binding.pager.setAdapter(mSectionsPagerAdapter);
        binding.pager.setCurrentItem(page);

        indicators.add(binding.page1Indicator);
        indicators.add(binding.page2Indicator);
        indicators.add(binding.page3Indicator);

        updateIndicators(page);

        binding.pager.addOnPageChangeListener(pagerListener);
        binding.next.setOnClickListener(nextListener);
        binding.previous.setOnClickListener(previousListener);
        binding.skip.setOnClickListener(v -> startActivity(new Intent(OnboardingActivity.this, LoginActivity.class)));

    }

    private void updateIndicators(int position) {
        for (int i = 0; i < indicators.size(); i++) {
            indicators.get(i).setBackgroundResource(i == position ? R.drawable.pager_item_selected : R.drawable.pager_item_unselected);
        }
    }


    private final ViewPager.OnPageChangeListener pagerListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            page = position;
            updateIndicators(page);
            binding.previous.setVisibility(page == 0 ? View.GONE : View.VISIBLE);
            binding.next.setText(page == 2 ? ONBOARDING_FINISH : ONBOARDING_NEXT);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private final View.OnClickListener nextListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (page != 2){
                page++;
                binding.pager.setCurrentItem(page, true);
            }
            else {
                sharedPreferences.edit().putBoolean(PREFERENCES_ONBOARDING, true).apply();
                startActivity(new Intent(OnboardingActivity.this, LoginActivity.class));

            }
        }
    };

    private final View.OnClickListener previousListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (page > 0) {
                page--;
                binding.pager.setCurrentItem(page, true);
            }
        }
    };




    /**
     * A placeholder fragment containing a simple view.
     */
    @SuppressWarnings("CanBeFinal")
    public static class PlaceholderFragment extends Fragment {

        private FragmentPagerBinding binding;

        private int[] bgs = new int[]{R.drawable.ironman, R.drawable.hellboy, R.drawable.margeto};


        public PlaceholderFragment() {}

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pager, container, false);

            int position = 0;
            if (getArguments() != null) {
                position = getArguments().getInt(ARG_SECTION_NUMBER);
            }

            binding.title.setText(getResources().getStringArray(R.array.onboarding_titles)[position]);
            binding.description.setText(getResources().getStringArray(R.array.onboarding_desc)[position]);

            Bitmap myBitmap = BitmapFactory.decodeResource(getResources(), bgs[position]);
            if (myBitmap != null && !myBitmap.isRecycled()) {
                Palette.from(myBitmap).generate(paletteListener);
            }
            binding.sectionImg.setBackgroundResource(bgs[position]);

            return binding.getRoot();
        }

        Palette.PaletteAsyncListener paletteListener = new Palette.PaletteAsyncListener() {
            public void onGenerated(@NonNull Palette palette) {
                int def = 0x000000;
                int vibrant = palette.getVibrantColor(def);

                binding.root.setBackgroundColor(vibrant);
            }
        };

    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {


        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position);

        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }

    }


}
