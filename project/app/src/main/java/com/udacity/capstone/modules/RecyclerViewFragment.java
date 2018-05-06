package com.udacity.capstone.modules;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.udacity.capstone.BaseFragment;
import com.udacity.capstone.R;
import com.udacity.capstone.data.model.Item;
import com.udacity.capstone.data.model.ItemList;
import com.udacity.capstone.databinding.FragmentItemsBinding;
import com.udacity.capstone.modules.detail.DetailActivity;

import org.parceler.Parcels;

import timber.log.Timber;

/**
 * Created by christosdemetriou on 27/04/2018.
 */

public class RecyclerViewFragment extends BaseFragment implements ItemAdapter.OnItemClickListener {

    FragmentItemsBinding binding;
    private String title;
    private int page;
    private ItemList list;

    public RecyclerViewFragment() {}

    public static Fragment newInstance() {
        return new RecyclerViewFragment();
    }


    public static Fragment newInstance(int page, String title, ItemList list) {
        Fragment fragmentFirst = RecyclerViewFragment.newInstance();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        args.putParcelable("list", Parcels.wrap(list));
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            page = getArguments().getInt("someInt", 0);
            title = getArguments().getString("someTitle");
            list = Parcels.unwrap(getArguments().getParcelable("list"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_items, container, false);

        Glide.with(this)
                .load(R.drawable.wallpaper)
                .apply(new RequestOptions().transforms(new CenterCrop()))
                .into(binding.background);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);

        binding.recyclerView.setLayoutManager(mLayoutManager);
        if (list == null) binding.progressBar.setVisibility(View.VISIBLE);
        else binding.progressBar.setVisibility(View.GONE);

        ItemAdapter adapter = new ItemAdapter(this, list);


        binding.recyclerView.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onItemClick(Item item) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("item", Parcels.wrap(item));
        if (list.isCharacter) {
            intent.putExtra("type", ItemList.Type.isCharacter);
            Timber.e("character click");
        }
        else if (list.isComic) {
            intent.putExtra("type", ItemList.Type.isComic);
            Timber.e("comic click");
        }
        startActivity(intent);

    }

    @Override
    public void onAddClick(Item item) {
        Timber.e("onAddClick");
    }
}
