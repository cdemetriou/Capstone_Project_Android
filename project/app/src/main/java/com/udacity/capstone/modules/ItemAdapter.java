package com.udacity.capstone.modules;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.udacity.capstone.R;
import com.udacity.capstone.data.model.Item;
import com.udacity.capstone.data.model.ItemList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by christosdemetriou on 27/04/2018.
 */



public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private ItemList list;
    private List<Item> mylist;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Item item);
        void onAddClick(Item item);
    }

    public ItemAdapter(OnItemClickListener listener, ItemList list) {
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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Item item = mylist.get(position);
        holder.bind(item, listener);

        if (list.isCharacter){
            holder.button.setVisibility(View.VISIBLE);
            holder.name.setText(item.getName() == null ? "Name" : item.getName());
        }
        else {
            holder.button.setVisibility(View.GONE);
            holder.name.setText(item.getTitle() == null ? "Title" : item.getTitle());
        }

        if (item.getThumbnail() != null){
            String image = item.getThumbnail().getPath() + "." + item.getThumbnail().getExtension();

            Glide.with(holder.image.getContext())
                    .load(image)
                    .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(90)))
                    .into(holder.image);
        }
    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }

    void setList(List<Item> newlist){
        mylist = newlist;
        notifyDataSetChanged();
    }


    void add(Item item){
        mylist.add(item);
        notifyItemChanged(getItemCount());
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        FrameLayout rootView;
        ImageView image;
        TextView name;
        FloatingActionButton button;

        ViewHolder(View itemView) {
            super(itemView);
            rootView = (FrameLayout) itemView;
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            button = itemView.findViewById(R.id.button);
        }

        public void bind(Item item, ItemAdapter.OnItemClickListener listener) {
            rootView.setOnClickListener(v -> listener.onItemClick(item));
            button.setOnClickListener(v -> listener.onAddClick(item));
        }
    }

}

