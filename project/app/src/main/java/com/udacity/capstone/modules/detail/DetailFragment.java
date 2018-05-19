package com.udacity.capstone.modules.detail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.udacity.capstone.BaseFragment;
import com.udacity.capstone.CapstoneApplication;
import com.udacity.capstone.R;
import com.udacity.capstone.firebase.FirebaseDatabaseManager;
import com.udacity.capstone.data.model.Item;
import com.udacity.capstone.databinding.FragmentItemDetailsBinding;

import org.parceler.Parcels;

import javax.inject.Inject;

import static com.udacity.capstone.data.Constants.EXTRAS_ITEM;
import static com.udacity.capstone.data.Constants.ITEM_DETAIL_SCREEN_NAME;


public class DetailFragment extends BaseFragment {

    @Inject
    FirebaseDatabaseManager databaseManager;

    private Item item = new Item();

    private static Fragment newInstance() {
        return new DetailFragment();
    }


    public static Fragment newInstance(Item item) {
        Fragment fragmentFirst = DetailFragment.newInstance();
        Bundle args = new Bundle();
        args.putParcelable(EXTRAS_ITEM, Parcels.wrap(item));
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            item = Parcels.unwrap(getArguments().getParcelable(EXTRAS_ITEM));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        CapstoneApplication.getApplicationComponent(getActivity()).inject(this);

        FragmentItemDetailsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_item_details, container, false);

        if (item.getThumbnail() != null) {
            String image = String.format("%s.%s", item.getThumbnail().getPath(), item.getThumbnail().getExtension());

            Glide.with(this)
                    .load(image)
                    .apply(new RequestOptions().transforms(new CenterCrop()))
                    .into(binding.image);

            binding.title.setText(item.getName() != null ? item.getName() : item.getTitle());
            binding.description.setText(item.getDescription() != null ? item.getDescription() : item.getVariantDescription());
        }

        return binding.getRoot();

    }

    @Override
    public String getScreenNameForAnalytics() {
        return ITEM_DETAIL_SCREEN_NAME;
    }

}