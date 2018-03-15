package com.davidju.bakingapp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.davidju.bakingapp.R;
import com.davidju.bakingapp.fragments.RecipeStepFragment;
import com.davidju.bakingapp.models.Step;

/**
 * Class that inflates the Fragment that displays a recipe step instruction.
 */
public class RecipeStepActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step);

        Step step = getIntent().getParcelableExtra("step");
        Bundle bundle = new Bundle();
        bundle.putParcelable("step", step);

        if (savedInstanceState == null) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            RecipeStepFragment fragment = new RecipeStepFragment();
            fragment.setArguments(bundle);
            transaction.add(R.id.frame_layout, fragment);
            transaction.commit();
        }
    }
}
