package com.udacity.capstone.modules.detail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.udacity.capstone.BaseFragment;
import com.udacity.capstone.R;
import com.udacity.capstone.databinding.FragmentItemDetailsBinding;

/**
 * Created by christosdemetriou on 04/05/2018.
 */


public class DetailFragment extends BaseFragment {

    FragmentItemDetailsBinding binding;
    private String title;
    private int page;

    public DetailFragment() {
    }

    public static Fragment newInstance() {
        return new DetailFragment();
    }


    public static Fragment newInstance(int page, String title) {
        Fragment fragmentFirst = DetailFragment.newInstance();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            page = getArguments().getInt("someInt", 0);
            title = getArguments().getString("someTitle");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_item_details, container, false);

        Glide.with(this)
                .load(R.drawable.margeto)
                .apply(new RequestOptions().transforms(new CenterCrop()))
                .into(binding.image);


        return binding.getRoot();
    }

}