package com.davidju.bakingapp.fragments;

import android.arch.lifecycle.Lifecycle;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.davidju.bakingapp.R;
import com.davidju.bakingapp.models.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment that controls the view in which the details for a particular recipe' step is displayed.
 */
public class RecipeStepFragment extends Fragment implements ExoPlayer.EventListener {

    private static final String TAG = RecipeStepFragment.class.getSimpleName();
    private static final String KEY_SCROLL_X_POSITION = "scroll_x_position";
    private static final String KEY_SCROLL_Y_POSITION = "scroll_y_position";
    private static final String KEY_PLAYER_POSITION = "exoplayer_position";
    private static final String KEY_PLAYER_PLAY_STATE = "exoplayer_play_state";
    private Unbinder unbinder;
    private SimpleExoPlayer exoPlayer;
    private MediaSessionCompat mediaSession;
    private PlaybackStateCompat.Builder playbackStateBuilder;
    private String videoUrl;
    private long playerPosition = 0;
    private boolean playState = true;
    @BindView(R.id.scroll_view) ScrollView scrollView;
    @BindView(R.id.image_header) TextView imageHeader;
    @BindView(R.id.image) ImageView image;
    @BindView(R.id.image_error) TextView imageError;
    @BindView(R.id.video_header) TextView videoHeader;
    @BindView(R.id.exoplayer) SimpleExoPlayerView exoPlayerView;
    @BindView(R.id.video_error) TextView videoError;
    @BindView(R.id.description) TextView description;

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_step, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        Step step = getArguments().getParcelable("step");

        String thumbnailUrl = step.getThumbnailUrl();
        if (!thumbnailUrl.isEmpty()) {
            Picasso.with(getContext())
                    .load(thumbnailUrl)
                    .into(image, new Callback() {
                        @Override
                        public void onSuccess() {}
                        @Override
                        public void onError() {
                            image.setVisibility(View.GONE);
                            imageError.setVisibility(View.VISIBLE);
                        }
                    });
        } else {
            imageHeader.setVisibility(View.GONE);
            image.setVisibility(View.GONE);
        }

        videoUrl = step.getVideoUrl();
        if (videoUrl.isEmpty()) {
            videoHeader.setVisibility(View.GONE);
            exoPlayerView.setVisibility(View.GONE);
        }
        description.setText(step.getDescription());

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!videoUrl.isEmpty()) {
            initializeMediaSession();
            initializePlayer(Uri.parse(videoUrl));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SCROLL_X_POSITION, scrollView.getScrollX());
        outState.putInt(KEY_SCROLL_Y_POSITION, scrollView.getScrollY());
        if (exoPlayer != null) {
            outState.putLong(KEY_PLAYER_POSITION, exoPlayer.getCurrentPosition());
            outState.putBoolean(KEY_PLAYER_PLAY_STATE, exoPlayer.getPlayWhenReady());
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            scrollView.post(() -> scrollView.scrollTo(savedInstanceState.getInt(KEY_SCROLL_X_POSITION),
                    savedInstanceState.getInt(KEY_SCROLL_Y_POSITION)));
            if (savedInstanceState.containsKey(KEY_PLAYER_POSITION)) {
                playerPosition = savedInstanceState.getLong(KEY_PLAYER_POSITION);
                playState = savedInstanceState.getBoolean(KEY_PLAYER_PLAY_STATE);
            }
        }
    }

    private void initializePlayer(Uri mediaUri) {
        if (exoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();

            exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            exoPlayerView.setPlayer(exoPlayer);

            DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            String userAgent = Util.getUserAgent(getContext(), "RecipeStepVideo");
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(), userAgent, bandwidthMeter);
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, dataSourceFactory,
                    new DefaultExtractorsFactory(), null, null);
            exoPlayer.prepare(mediaSource);
            exoPlayer.seekTo(playerPosition);
            exoPlayer.setPlayWhenReady(playState);
        }
    }

    private void initializeMediaSession() {
        mediaSession = new MediaSessionCompat(getContext(), TAG);
        mediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mediaSession.setMediaButtonReceiver(null);

        playbackStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(PlaybackStateCompat.ACTION_PLAY | PlaybackStateCompat.ACTION_PAUSE
                            | PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS | PlaybackStateCompat.ACTION_PLAY_PAUSE);
        mediaSession.setPlaybackState(playbackStateBuilder.build());
        mediaSession.setCallback(new MediaSessionCallback());
        mediaSession.setActive(true);
    }

    private void releasePlayer() {
        if (exoPlayer != null) {
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    private void releaseMediaSession() {
        if (mediaSession != null) {
            mediaSession.setActive(false);
            mediaSession.release();
            mediaSession = null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (exoPlayer != null) {
            playState = exoPlayer.getPlayWhenReady();
            playerPosition = exoPlayer.getCurrentPosition();
        }
        releasePlayer();
        releaseMediaSession();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public Lifecycle getLifecycle() {
        return super.getLifecycle();
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {}

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {}

    @Override
    public void onLoadingChanged(boolean isLoading) {
        Log.d(TAG, "exoplayer loading changed: " + isLoading);
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if (playbackState == ExoPlayer.STATE_READY) {
            if (playWhenReady) {
                Log.d(TAG, "exoplayer state changed: PLAYING");
                playbackStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING, exoPlayer.getCurrentPosition(), 1f);
            } else {
                Log.d(TAG, "exoplayer state changed: PAUSED");
                playbackStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED, exoPlayer.getCurrentPosition(), 1f);
            }
        }
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
        Log.d(TAG, "exoplayer error: " + error.getMessage());
        exoPlayerView.setVisibility(View.GONE);
        videoError.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPositionDiscontinuity() {}

    /* Media Session callbacks for when external clients control the player. */
    private class MediaSessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            exoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            exoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            exoPlayer.seekTo(0);
        }
    }
}
