package com.davidju.bakingapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davidju.bakingapp.R;
import com.davidju.bakingapp.adapters.RecipeDetailsAdapter;
import com.davidju.bakingapp.models.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsFragment extends Fragment {

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    RecipeDetailsAdapter adapter;

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        ButterKnife.bind(this, rootView);

        Recipe recipe = getArguments().getParcelable("recipe");
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RecipeDetailsAdapter(recipe);
        recyclerView.setAdapter(adapter);


        return rootView;
    }
}
