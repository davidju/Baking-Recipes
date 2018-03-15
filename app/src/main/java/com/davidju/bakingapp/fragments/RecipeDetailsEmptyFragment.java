package com.davidju.bakingapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davidju.bakingapp.R;

/**
 * Fragment for two pane recipe details layout, inflated in the very beginning when a step has not been selected yet.
 */
public class RecipeDetailsEmptyFragment extends Fragment {

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe_details_empty, container, false);
    }
}
