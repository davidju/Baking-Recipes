package com.davidju.bakingapp.providers;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;

import com.davidju.bakingapp.R;
import com.davidju.bakingapp.activities.RecipeDetailsActivity;
import com.davidju.bakingapp.activities.RecipeSelectionActivity;
import com.davidju.bakingapp.models.Ingredient;
import com.davidju.bakingapp.models.Recipe;

import java.util.List;

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
                StringBuilder builder = new StringBuilder();
                for (Ingredient ingredient : ingredients) {
                    String str = "- " + ingredient.getQuantity() + " " + ingredient.getMeasure()
                            + " " + ingredient.getIngredient() + "\n";
                    builder.append(str);
                }
                String ingredientsStr = builder.toString();

                updateAppWidget(context, widgetId, name, ingredientsStr, recipe);
            }
        }
    }

    private void updateAppWidget(Context context, int appWidgetId, String name, String ingredients,
                                 Recipe recipe) {
        Intent recipeIntent = new Intent(context, RecipeDetailsActivity.class);
        recipeIntent.putExtra("recipe", recipe);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, recipeIntent, 0);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_recipe);
        views.setOnClickPendingIntent(R.id.widget_view, pendingIntent);

        views.setTextViewText(R.id.widget_recipe_name, name);
        views.setViewVisibility(R.id.widget_recipe_name, View.VISIBLE);

        views.setTextViewText(R.id.widget_recipe_ingredients, ingredients);
        views.setViewVisibility(R.id.widget_recipe_ingredients, View.VISIBLE);

        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.updateAppWidget(appWidgetId, views);
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
