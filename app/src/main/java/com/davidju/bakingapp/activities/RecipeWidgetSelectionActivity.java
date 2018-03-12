package com.davidju.bakingapp.activities;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

import com.davidju.bakingapp.R;
import com.davidju.bakingapp.adapters.RecipeWidgetSelectionAdapter;
import com.davidju.bakingapp.interfaces.OnRecipeSelectedListener;
import com.davidju.bakingapp.providers.RecipeWidgetProvider;

import butterknife.ButterKnife;

public class RecipeWidgetSelectionActivity extends RecipeSelectionActivity implements OnRecipeSelectedListener {

    private int widgetId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_selection);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            widgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        Configuration config = getResources().getConfiguration();
        if (config.smallestScreenWidthDp >= 600) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

        adapter = new RecipeWidgetSelectionAdapter();
        ((RecipeWidgetSelectionAdapter) adapter).callback = this;
        recyclerView.setAdapter(adapter);

        fetchRecipes();
    }

    @Override
    public void onRecipeSelected(int position) {
        Intent updateIntent = new Intent(this, RecipeWidgetProvider.class);
        updateIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        updateIntent.putExtra("recipe", ((RecipeWidgetSelectionAdapter) adapter).getRecipe(position));
        updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        sendBroadcast(updateIntent);

        Intent intent = new Intent();
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        setResult(RESULT_OK, intent);
        finish();
    }
}
