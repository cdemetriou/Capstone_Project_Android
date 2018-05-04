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
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.udacity.capstone.R;
import com.udacity.capstone.data.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by christosdemetriou on 27/04/2018.
 */



public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private List<Item> list;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Item item);
        void onAddClick(Item item);
    }

    public ItemAdapter(OnItemClickListener listener) {
        list = new ArrayList<>();
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

        Item item = list.get(position);
        holder.bind(item, listener);

        holder.name.setText(item.getName() == null ? "Name" : item.getName());
        Glide.with(holder.image.getContext())
                .load(item.getThumbnail() != null ? item.getThumbnail() : R.drawable.daredevil)
                .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(90)))
                .into(holder.image);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    void setList(List<Item> newlist){
        list = newlist;
        notifyDataSetChanged();
    }


    void add(Item item){
        list.add(item);
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

