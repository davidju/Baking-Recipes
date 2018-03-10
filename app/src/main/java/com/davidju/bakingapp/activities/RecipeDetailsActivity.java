package com.davidju.bakingapp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.davidju.bakingapp.R;
import com.davidju.bakingapp.fragments.RecipeDetailsFragment;
import com.davidju.bakingapp.models.Recipe;

public class RecipeDetailsActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        Recipe recipe = getIntent().getParcelableExtra("recipe");
        Bundle bundle = new Bundle();
        bundle.putParcelable("recipe", recipe);

        final boolean twoPane = findViewById(R.id.frame_layout) == null;

        if (savedInstanceState == null) {

            if (!twoPane) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                RecipeDetailsFragment fragment = new RecipeDetailsFragment();
                fragment.setArguments(bundle);
                transaction.add(R.id.frame_layout, fragment);
                transaction.commit();
            } else {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();

                RecipeDetailsFragment fragment = new RecipeDetailsFragment();
                fragment.setArguments(bundle);
                transaction.add(R.id.master_list, fragment);
                transaction.commit();
            }
        }
    }
}
