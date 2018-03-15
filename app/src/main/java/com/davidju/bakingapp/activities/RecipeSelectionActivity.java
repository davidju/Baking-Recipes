package com.davidju.bakingapp.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.davidju.bakingapp.AppIdlingResource;
import com.davidju.bakingapp.R;
import com.davidju.bakingapp.adapters.RecipeSelectionAdapter;
import com.davidju.bakingapp.models.Ingredient;
import com.davidju.bakingapp.models.Recipe;
import com.davidju.bakingapp.models.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Class that controls the main view that displays list of available recipes for the user to view.
 */
public class RecipeSelectionActivity extends AppCompatActivity {

    protected static final String KEY_STATE = "recipe_selection_state";
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    RecipeSelectionAdapter adapter;
    Parcelable layoutState;
    @Nullable private AppIdlingResource idlingResource;

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (idlingResource == null) {
            idlingResource = new AppIdlingResource();
        }
        return idlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_selection);
        ButterKnife.bind(this);

        Configuration config = getResources().getConfiguration();
        if (config.smallestScreenWidthDp >= 600) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }

        adapter = new RecipeSelectionAdapter();
        recyclerView.setAdapter(adapter);

        getIdlingResource();
        if (idlingResource != null) {
            idlingResource.setIdleState(false);
        }

        fetchRecipes();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_STATE, recyclerView.getLayoutManager().onSaveInstanceState());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            layoutState = savedInstanceState.getParcelable(KEY_STATE);
        }
    }

    /* Fetches recipes from network source and parses the JSON result to models */
    protected void fetchRecipes() {
        final String dataUrl = "https://d17h27t6h515a5.cloudfront.net/topher/" +
                "2017/May/59121517_baking/baking.json";

        final String KEY_ID = "id";
        final String KEY_NAME = "name";
        final String KEY_SERVINGS = "servings";
        final String KEY_IMAGE = "image";

        final String KEY_INGREDIENTS = "ingredients";
        final String KEY_QUANTITY = "quantity";
        final String KEY_MEASURE = "measure";
        final String KEY_INGREDIENT = "ingredient";

        final String KEY_STEPS = "steps";
        final String KEY_SHORT_DESCRIPTION = "shortDescription";
        final String KEY_DESCRIPTION = "description";
        final String KEY_VIDEO_URL = "videoURL";
        final String KEY_THUMBNAIL_URL = "thumbnailURL";

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, dataUrl, null, (JSONArray response) -> {
            List<Recipe> recipes = new ArrayList<>();

            for (int i = 0; i < response.length(); i++) {
                Recipe recipe = new Recipe();
                try {
                    // Parse recipe info
                    JSONObject recipeJson = response.getJSONObject(i);
                    recipe.setId(recipeJson.optInt(KEY_ID));
                    recipe.setName(recipeJson.optString(KEY_NAME));
                    recipe.setServings(recipeJson.optInt(KEY_SERVINGS));
                    recipe.setImage(recipeJson.optString(KEY_IMAGE));

                    // Parse ingredient info
                    JSONArray ingredients = recipeJson.getJSONArray(KEY_INGREDIENTS);
                    for (int j = 0; j < ingredients.length(); j++) {
                        Ingredient ingredient = new Ingredient();
                        JSONObject ingredientJson = ingredients.getJSONObject(j);
                        ingredient.setQuantity(ingredientJson.optInt(KEY_QUANTITY));
                        ingredient.setMeasure(ingredientJson.optString(KEY_MEASURE));
                        ingredient.setIngredient(ingredientJson.optString(KEY_INGREDIENT));
                        recipe.addIngredient(ingredient);
                    }

                    // Parse recipe instruction info
                    JSONArray steps = recipeJson.getJSONArray(KEY_STEPS);
                    for (int j = 0; j < steps.length(); j++) {
                        Step step = new Step();
                        JSONObject stepJson = steps.getJSONObject(j);
                        step.setId(stepJson.optInt(KEY_ID));
                        step.setShortDescription(stepJson.optString(KEY_SHORT_DESCRIPTION));
                        step.setDescription(stepJson.optString(KEY_DESCRIPTION));
                        step.setVideoUrl(stepJson.optString(KEY_VIDEO_URL));
                        step.setThumbnailUrl(stepJson.optString(KEY_THUMBNAIL_URL));
                        recipe.addStep(step);
                    }

                    recipes.add(recipe);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            adapter.updateRecipes(recipes);
            adapter.notifyDataSetChanged();
            if (layoutState != null) {
                recyclerView.getLayoutManager().onRestoreInstanceState(layoutState);
                layoutState = null;
            }

            if (idlingResource != null) {
                idlingResource.setIdleState(true);
            }

        }, VolleyError::printStackTrace);

        queue.add(request);
    }

}
