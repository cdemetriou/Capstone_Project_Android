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
import com.udacity.capstone.data.model.Item;
import com.udacity.capstone.databinding.FragmentItemDetailsBinding;

import org.parceler.Parcels;

import static com.udacity.capstone.data.model.ItemList.Type.isCharacter;

/**
 * Created by christosdemetriou on 04/05/2018.
 */


public class DetailFragment extends BaseFragment {

    FragmentItemDetailsBinding binding;
    private String title;
    private int page;
    private Item item = new Item();
    private int type;

    public DetailFragment() {
    }

    public static Fragment newInstance() {
        return new DetailFragment();
    }


    public static Fragment newInstance(Item item, int type) {
        Fragment fragmentFirst = DetailFragment.newInstance();
        Bundle args = new Bundle();
        args.putParcelable("item", Parcels.wrap(item));
        args.putInt("type", type);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            item = Parcels.unwrap(getArguments().getParcelable("item"));
            type = getArguments().getInt("type");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_item_details, container, false);

        if (type != isCharacter) binding.button.setVisibility(View.GONE);

        if (item.getThumbnail() != null) {
            String image = item.getThumbnail().getPath() + "." + item.getThumbnail().getExtension();

            Glide.with(this)
                    .load(image)
                    .apply(new RequestOptions().transforms(new CenterCrop()))
                    .into(binding.image);

            binding.title.setText(item.getName() != null ? item.getName() : item.getTitle());
            binding.description.setText(item.getDescription() != null ? item.getDescription() : item.getVariantDescription());
        }



        return binding.getRoot();
    }

}