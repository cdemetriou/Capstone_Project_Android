package com.udacity.capstone.modules.recyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.udacity.capstone.CapstoneApplication;
import com.udacity.capstone.R;
import com.udacity.capstone.firebase.FirebaseDatabaseManager;
import com.udacity.capstone.data.model.Item;
import com.udacity.capstone.data.model.ItemList;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.udacity.capstone.data.Constants.PREFERENCES_EXPLANATION;


@SuppressWarnings({"WeakerAccess", "unused"})
public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static int ITEM = 0;
    private final static int LOADING = 1;

    private ItemList list;
    private List<Item> mylist;
    private final OnItemClickListener listener;

    @Inject
    FirebaseDatabaseManager databaseManager;

    @Inject
    SharedPreferences sharedPreferences;

    public interface OnItemClickListener {
        void onItemClick(Item item);
        void onAddClick(Item item);
        void onRemoveClick(Item item);
    }

    ItemAdapter(Context context, OnItemClickListener listener, ItemList list) {
        CapstoneApplication.getApplicationComponent(context).inject(this);

        if (list != null) {
            this.list = list;
            this.mylist = list.getList();
        }
        else {
            this.mylist = new ArrayList<>();
        }
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;

        switch (viewType) {
            case ITEM:
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
                viewHolder = new ViewHolder(itemView);
                break;
            case LOADING:
                View loadingView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
                viewHolder = new LoadViewHolder(loadingView);
                break;
            default:
                View defItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
                viewHolder = new ViewHolder(defItemView);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewholder, int position) {
        switch (viewholder.getItemViewType()) {
            case LOADING:
                break;
            case ITEM:
                ViewHolder holder = (ViewHolder) viewholder;
                Item item = mylist.get(position);
                holder.bind(item, listener);

                if (list.getType() == ItemList.Type.isCharacter)
                    holder.name.setText(item.getName() == null ? holder.name.getContext().getString(R.string.name) : item.getName());
                else holder.name.setText(item.getTitle() == null ? holder.name.getContext().getString(R.string.title) : item.getTitle());

                if (list.getType() == ItemList.Type.isCharacter && !databaseManager.favoriteIds.contains(item.getId())) {
                    holder.button.setVisibility(View.VISIBLE);
                    holder.button.setImageResource(android.R.drawable.ic_input_add);
                    holder.button.setBackgroundTintList(holder.button.getContext().getResources().getColorStateList(R.color.accent));
                } else holder.button.setVisibility(View.GONE);

                if (databaseManager.favoriteIds.contains(item.getId())) {
                    holder.button.setImageResource(R.drawable.ic_input_remove);
                    holder.button.setBackgroundTintList(holder.button.getContext().getResources().getColorStateList(android.R.color.holo_red_light));
                }

                if (databaseManager.favoriteIds.contains(item.getId())) {
                    holder.button.setVisibility(View.GONE);
                }

                if (item.getThumbnail() != null) {
                    String image = item.getThumbnail().getPath() + holder.rootView.getResources().getString(R.string.fullstop) + item.getThumbnail().getExtension();

                    Glide.with(holder.image.getContext())
                            .load(image)
                            .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(90)))
                            .into(holder.image);
                }

                if (position == 0 && databaseManager.favoriteIds.contains(item.getId()) && !sharedPreferences.getBoolean(PREFERENCES_EXPLANATION, false)) {
                    holder.sample_button.setImageResource(R.drawable.ic_input_remove);
                    holder.sample_button.setBackgroundTintList(holder.button.getContext().getResources().getColorStateList(android.R.color.holo_red_light));
                    holder.explanationCardView.setVisibility(View.VISIBLE);
                }

        }
    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mylist.get(position) == null ? LOADING : ITEM;
    }

    void setList(List<Item> newlist){
        mylist = newlist;
        notifyDataSetChanged();
    }

    void addAll(List<Item> mcList) {
        for (Item mc : mcList) {
            add(mc);
        }
    }

    void add(Item item){
        mylist.add(item);
        notifyItemChanged(getItemCount());
    }

    void remove(Item item) {
        int position = mylist.indexOf(item);
        if (position > -1) {
            mylist.remove(position);
            notifyItemRemoved(position);
        }
    }

    Item getItem(int position) {
        return mylist.get(position);
    }


    @SuppressWarnings("CanBeFinal")
    class ViewHolder extends RecyclerView.ViewHolder {

        FrameLayout rootView, explanationCardView;
        ImageView image;
        TextView name;
        FloatingActionButton button, sample_button;
        boolean removeButtonShown = false;

        ViewHolder(View itemView) {
            super(itemView);
            rootView = (FrameLayout) itemView;
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            button = itemView.findViewById(R.id.button);
            explanationCardView = itemView.findViewById(R.id.explanation_card);
            sample_button = itemView.findViewById(R.id.button_sample);
        }

        public void bind(Item item, OnItemClickListener listener) {
            rootView.setOnClickListener(v -> listener.onItemClick(item));
            explanationCardView.setOnClickListener(v -> listener.onItemClick(item));

            if (databaseManager.favoriteIds.contains(item.getId())) {
                rootView.setOnLongClickListener(v -> {
                    explanationCardView.setVisibility(View.GONE);
                    sharedPreferences.edit().putBoolean(PREFERENCES_EXPLANATION, true).apply();
                    if (removeButtonShown) {
                        button.setVisibility(View.GONE);
                        removeButtonShown = false;
                    } else {
                        button.setVisibility(View.VISIBLE);
                        YoYo.with(Techniques.Shake)
                                .duration(600)
                                .playOn(rootView);
                        removeButtonShown = true;
                    }
                    return true;
                });

                explanationCardView.setOnLongClickListener(v -> {
                    explanationCardView.setVisibility(View.GONE);
                    sharedPreferences.edit().putBoolean(PREFERENCES_EXPLANATION, true).apply();
                    if (removeButtonShown) {
                        button.setVisibility(View.GONE);
                        removeButtonShown = false;
                    } else {
                        button.setVisibility(View.VISIBLE);
                        YoYo.with(Techniques.Shake)
                                .duration(600)
                                .playOn(rootView);
                        removeButtonShown = true;
                    }
                    return true;
                });
            }

            button.setOnClickListener((View v) -> {
                explanationCardView.setVisibility(View.GONE);
                if (databaseManager.favoriteIds.contains(item.getId())) {
                    listener.onRemoveClick(item);
                    button.setVisibility(View.VISIBLE);
                    button.setImageResource(android.R.drawable.ic_input_add);
                    button.setBackgroundTintList(button.getContext().getResources().getColorStateList(R.color.accent));
                }
                else {
                    listener.onAddClick(item);
                    button.setVisibility(View.GONE);
                }
            });
        }
    }

    class LoadViewHolder extends RecyclerView.ViewHolder {
        LoadViewHolder(View itemView) {super(itemView);}
    }

}

