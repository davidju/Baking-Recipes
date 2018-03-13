package com.davidju.bakingapp.services;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.davidju.bakingapp.R;
import com.davidju.bakingapp.models.Ingredient;
import com.davidju.bakingapp.models.Recipe;

import java.util.List;

public class RecipeWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeWidgetViewsFactory(this.getApplicationContext(), intent);
    }

    class RecipeWidgetViewsFactory implements RemoteViewsFactory {

        Context context;
        String name;
        List<String> ingredients;

        RecipeWidgetViewsFactory(Context context, Intent intent) {
            this.context = context;
            name = intent.getStringExtra("recipe");
            ingredients = intent.getStringArrayListExtra("ingredients");
        }

        @Override
        public void onCreate() {
        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {
        }

        @Override
        public int getCount() {
            return ingredients.size() + 1;
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews view;
            if (position == 0) {
                view = new RemoteViews(context.getPackageName(), R.layout.item_widget_recipe);
                view.setTextViewText(R.id.recipe, name);
            } else {
                view = new RemoteViews(context.getPackageName(), R.layout.item_widget_ingredient);
                view.setTextViewText(R.id.ingredient, ingredients.get(position - 1));
            }

            return view;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
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
