package com.udacity.capstone.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udacity.capstone.CapstoneApplication;
import com.udacity.capstone.R;
import com.udacity.capstone.data.model.Item;
import com.udacity.capstone.data.model.ItemList;

import java.util.ArrayList;



public class RemoteViewsAdapter extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new IngredientsViewsFactory(this.getApplicationContext());
    }

    @SuppressWarnings("CanBeFinal")
    class IngredientsViewsFactory implements RemoteViewsFactory {
        Context context;
        ArrayList<Item> ingredients = new ArrayList<>();


        public IngredientsViewsFactory(Context context) {
            this.context = context;
            ItemList recipe = CapstoneApplication.getWidgetManager().getRecipe();
            if (recipe != null) ingredients = (ArrayList<Item>) recipe.getList();
        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return ingredients.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_item);
            views.setTextViewText(R.id.text_view, ingredients.get(position).getName());

            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}