package com.davidju.bakingapp.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.davidju.bakingapp.R;
import com.davidju.bakingapp.models.Recipe;

public class RecipeDetailsActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        Recipe recipe = getIntent().getParcelableExtra("recipe");
 
    }
}
