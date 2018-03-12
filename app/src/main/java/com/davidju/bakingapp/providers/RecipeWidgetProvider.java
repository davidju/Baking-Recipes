package com.davidju.bakingapp.providers;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.davidju.bakingapp.R;
import com.davidju.bakingapp.activities.RecipeSelectionActivity;
import com.davidju.bakingapp.models.Recipe;

public class RecipeWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        Intent intent = new Intent(context, RecipeSelectionActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_recipe);
        views.setOnClickPendingIntent(R.id.widget_text, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.hasExtra(AppWidgetManager.EXTRA_APPWIDGET_ID) &&
                intent.hasExtra("recipe")) {
            int widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
            Recipe recipe = intent.getParcelableExtra("recipe");

            if (widgetId != -1) {
                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_recipe);
                views.setTextViewText(R.id.widget_text, recipe.getName());
                AppWidgetManager manager = AppWidgetManager.getInstance(context);
                manager.updateAppWidget(widgetId, views);
            }
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }
}
