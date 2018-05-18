package com.udacity.capstone.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.udacity.capstone.R;
import com.udacity.capstone.data.model.ItemList;



@SuppressWarnings({"WeakerAccess", "CanBeFinal"})
public class WidgetManager {
    private Context context;
    private ItemList recipe;


    public WidgetManager(Context context) {
        this.context = context;
    }

    public void setRecipe(ItemList recipe) {
        this.recipe = recipe;
        updateWidget();
    }

    public ItemList getRecipe() {
        return recipe;
    }

    public void updateWidget() {
        Intent intent = new Intent(context, AppWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName componentName = new ComponentName(context, AppWidget.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(componentName);

        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

        context.sendBroadcast(intent);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.list_view);
    }
}