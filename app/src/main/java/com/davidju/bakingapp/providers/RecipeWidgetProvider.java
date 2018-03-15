package com.davidju.bakingapp.providers;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.davidju.bakingapp.R;
import com.davidju.bakingapp.activities.RecipeDetailsActivity;
import com.davidju.bakingapp.models.Ingredient;
import com.davidju.bakingapp.models.Recipe;
import com.davidju.bakingapp.services.RecipeWidgetService;

import java.util.ArrayList;
import java.util.List;

/**
 * Provider class used to manage widget actions when particular widget broadcasts are received.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.hasExtra(AppWidgetManager.EXTRA_APPWIDGET_ID) &&
                intent.hasExtra("recipe")) {
            int widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
            Recipe recipe = intent.getParcelableExtra("recipe");

            if (widgetId != -1) {
                String name = recipe.getName();
                List<Ingredient> ingredients = recipe.getIngredients();
                List<String> ingredientList = new ArrayList<>();
                for (Ingredient ingredient : ingredients) {
                    String str = "- " + ingredient.getQuantity() + " " + ingredient.getMeasure()
                            + " " + ingredient.getIngredient();
                    ingredientList.add(str);
                }

                updateAppWidget(context, widgetId, name, ingredientList, recipe);
            }
        }
    }

    /* Helper method to execute all actions associated with updating a widget */
    private void updateAppWidget(Context context, int appWidgetId, String name, List<String> ingredients,
                                 Recipe recipe) {
        // Send intent to update widget matching the specified widgetId with the appropriate data
        Intent serviceIntent = new Intent(context, RecipeWidgetService.class);
        serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        serviceIntent.putExtra("recipe", name);
        serviceIntent.putStringArrayListExtra("ingredients", new ArrayList<>(ingredients));
        serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));

        // Get widget and set adapter to the widget
        RemoteViews widget = new RemoteViews(context.getPackageName(), R.layout.widget_recipe);
        widget.setRemoteAdapter(appWidgetId, R.id.list_view, serviceIntent);

        // Set pending intent action for when the widget is clicked
        Intent recipeIntent = new Intent(context, RecipeDetailsActivity.class);
        recipeIntent.putExtra("recipe", recipe);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, recipeIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        widget.setPendingIntentTemplate(R.id.list_view, pendingIntent);

        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.updateAppWidget(appWidgetId, widget);
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
