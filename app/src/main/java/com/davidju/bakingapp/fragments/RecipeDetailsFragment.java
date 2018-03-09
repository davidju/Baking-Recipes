package com.davidju.bakingapp.fragments;

import android.os.Bundle;
import android.os.Parcelable;
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
import butterknife.Unbinder;

public class RecipeDetailsFragment extends Fragment {

    private static final String KEY_STATE = "recipe_details_state";
    private Unbinder unbinder;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    RecipeDetailsAdapter adapter;
    Parcelable layoutState;

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        Recipe recipe = getArguments().getParcelable("recipe");
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RecipeDetailsAdapter(recipe);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_STATE, recyclerView.getLayoutManager().onSaveInstanceState());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            layoutState = savedInstanceState.getParcelable(KEY_STATE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (layoutState != null) {
            recyclerView.getLayoutManager().onRestoreInstanceState(layoutState);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
