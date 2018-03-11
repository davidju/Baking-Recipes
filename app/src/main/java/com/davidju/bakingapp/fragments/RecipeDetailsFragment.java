package com.davidju.bakingapp.fragments;

import android.content.Context;
import android.content.Intent;
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
import com.davidju.bakingapp.activities.RecipeStepActivity;
import com.davidju.bakingapp.adapters.RecipeDetailsAdapter;
import com.davidju.bakingapp.interfaces.OnStepClickedListener;
import com.davidju.bakingapp.models.Recipe;
import com.davidju.bakingapp.models.Step;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RecipeDetailsFragment extends Fragment implements OnStepClickedListener {

    private static final String KEY_STATE = "recipe_details_state";
    private Unbinder unbinder;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    RecipeDetailsAdapter adapter;
    Parcelable layoutState;
    OnStepClickedListener callback;
    Recipe recipe;
    boolean twoPane;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callback = (OnStepClickedListener) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        twoPane = getArguments().getBoolean("two_pane");
        recipe = getArguments().getParcelable("recipe");
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RecipeDetailsAdapter(recipe);
        adapter.callback = this;
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onStepSelected(int position) {
        if (!twoPane) {
            Step step = recipe.getSteps().get(position - RecipeDetailsAdapter.buffer);
            Intent intent = new Intent(getContext(), RecipeStepActivity.class);
            intent.putExtra("step", step);
            startActivity(intent);
        } else {
            callback.onStepSelected(position);
        }
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
            layoutState = null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
