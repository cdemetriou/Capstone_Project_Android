package com.udacity.capstone.modules;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.udacity.capstone.BaseFragment;
import com.udacity.capstone.R;
import com.udacity.capstone.data.model.Item;
import com.udacity.capstone.databinding.FragmentItemsBinding;
import com.udacity.capstone.databinding.ItemLayoutBinding;

import timber.log.Timber;

/**
 * Created by christosdemetriou on 27/04/2018.
 */

public class CharactersFragment extends BaseFragment implements ItemAdapter.OnItemClickListener {

    FragmentItemsBinding binding;

    public CharactersFragment() {}

    public static Fragment newInstance() {
        return new CharactersFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_items, container, false);

        Glide.with(this)
                .load(R.drawable.wallpaper)
                .apply(new RequestOptions().transforms(new CenterCrop()))
                .into(binding.background);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);

        binding.recyclerView.setLayoutManager(mLayoutManager);

        ItemAdapter adapter = new ItemAdapter(this);
        binding.recyclerView.setAdapter(adapter);

        for (int i = 0; i < 6; i++) adapter.add(new Item());

        return binding.getRoot();
    }

    @Override
    public void onItemClick(Item item) {
        Timber.e("onItemClick");
        /*FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
        ListingFragment listingFragment = ListingFragment.newInstance();
        transaction.addToBackStack(null);
        transaction.add(R.id.frame_layout, listingFragment).commit();*/
    }

    @Override
    public void onAddClick(Item item) {
        Timber.e("onAddClick");
    }
}
