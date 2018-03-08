package com.davidju.bakingapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.davidju.bakingapp.R;
import com.davidju.bakingapp.models.Step;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String description;
    private String videoUrl;

    public RecipeStepAdapter(Step step) {
        description = step.getDescription();
        videoUrl = step.getVideoUrl();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_step_video, parent, false);
            return new VideoViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_step_description, parent, false);
            return new DescriptionViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder.getItemViewType() == 0) {

        } else {
            ((DescriptionViewHolder) viewHolder).description.setText(description);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    class VideoViewHolder extends RecyclerView.ViewHolder {

        VideoViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class DescriptionViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.description) TextView description;

        DescriptionViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
