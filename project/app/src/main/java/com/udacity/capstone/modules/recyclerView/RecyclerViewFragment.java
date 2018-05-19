package com.udacity.capstone.modules.recyclerView;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.udacity.capstone.CapstoneApplication;
import com.udacity.capstone.R;
import com.udacity.capstone.firebase.FirebaseDatabaseManager;
import com.udacity.capstone.data.model.Item;
import com.udacity.capstone.data.model.ItemList;
import com.udacity.capstone.databinding.FragmentItemsBinding;
import com.udacity.capstone.modules.detail.DetailActivity;

import org.parceler.Parcels;

import javax.inject.Inject;


import static com.udacity.capstone.data.Constants.EXTRAS_FAVORITES;
import static com.udacity.capstone.data.Constants.EXTRAS_ITEM;
import static com.udacity.capstone.data.Constants.EXTRAS_LIST;
import static com.udacity.capstone.data.Constants.EXTRAS_SEARCH_TERM;
import static com.udacity.capstone.data.Constants.EXTRAS_TYPE;
import static com.udacity.capstone.data.Constants.RECYCLERVIEW_SCREEN_NAME;



@SuppressWarnings("WeakerAccess")
public class RecyclerViewFragment extends BaseFragment implements ItemAdapter.OnItemClickListener {

    private FragmentItemsBinding binding;
    private ItemList list;
    private ItemAdapter adapter;

    private String searchTerm;
    private boolean favorites;
    private boolean foundend = false;

    private RecyclerViewModel viewModel;

    @Inject
    FirebaseDatabaseManager databaseManager;

    public RecyclerViewFragment() {}

    private static Fragment newInstance() {
        return new RecyclerViewFragment();
    }


    public static Fragment newInstance(boolean favorites, String searchTerm, ItemList list) {
        Fragment fragmentFirst = RecyclerViewFragment.newInstance();
        Bundle args = new Bundle();
        args.putBoolean(EXTRAS_FAVORITES, favorites);
        args.putString(EXTRAS_SEARCH_TERM, searchTerm);
        args.putParcelable(EXTRAS_LIST, Parcels.wrap(list));
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            list = Parcels.unwrap(getArguments().getParcelable(EXTRAS_LIST));
            searchTerm = getArguments().getString(EXTRAS_SEARCH_TERM, null);
            favorites = getArguments().getBoolean(EXTRAS_FAVORITES, false);
        }
    }

    @Override
    public String getScreenNameForAnalytics() {
        return RECYCLERVIEW_SCREEN_NAME;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        CapstoneApplication.getApplicationComponent(getActivity()).inject(this);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_items, container, false);

        viewModel = new RecyclerViewModel(getContext(), list, searchTerm);

        Glide.with(this)
                .load(R.drawable.wallpaper)
                .apply(new RequestOptions().transforms(new CenterCrop()))
                .into(binding.background);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);

        binding.recyclerView.setLayoutManager(mLayoutManager);

        if (list == null) binding.progressBar.setVisibility(View.VISIBLE);
        else binding.progressBar.setVisibility(View.GONE);

        adapter = new ItemAdapter(getActivity(),this, list);

        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.addOnScrollListener(listener);

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(Item item) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(EXTRAS_ITEM, Parcels.wrap(item));
        intent.putExtra(EXTRAS_TYPE, list.getType());

        startActivity(intent);
    }

    @Override
    public void onAddClick(Item item) {
        databaseManager.addFavorite(item);
    }

    @Override
    public void onRemoveClick(Item item) {
        databaseManager.removeFavorite(item);
    }


    private final RecyclerView.OnScrollListener listener = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            if (!recyclerView.canScrollVertically(1) && !foundend && !favorites) {

                if (list == null || list.getList() == null) return;

                foundend = true;
                adapter.add(null);
                int index =  (adapter.getItemCount() == 0) ? 0 : adapter.getItemCount() - 1;
                binding.recyclerView.scrollToPosition(index);

                viewModel.getMore();
                viewModel.getList().observe(RecyclerViewFragment.this, myObserver);
            }
        }
    };


    private final Observer<ItemList> myObserver = new Observer<ItemList>() {
        @Override
        public void onChanged(@Nullable final ItemList list) {

            if (list != null && list.getList() != null){
                adapter.remove(adapter.getItem(adapter.getItemCount() - 1));
                int index =  (adapter.getItemCount() == 0) ? 0 : adapter.getItemCount() - 1;
                adapter.addAll(list.getList());
                binding.recyclerView.scrollToPosition(index);
                foundend = false;
            }

        }
    };

}
