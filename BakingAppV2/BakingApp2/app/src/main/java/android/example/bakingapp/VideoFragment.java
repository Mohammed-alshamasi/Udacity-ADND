package android.example.bakingapp;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.example.bakingapp.Data.Steps;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import static android.example.bakingapp.DetailActivity.isTablet;

public class VideoFragment extends Fragment {
    Steps step = null;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private ImageView imageView;
    boolean noVid;
    TextView textView;
    static final String NO_VID="no vid";
    static final String STEP="STEP";
    static final String URL="url";
    String videoUrl=null;

    public VideoFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.video_fragment, container, false);

        if (!isTablet)
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        if(savedInstanceState!=null){
            noVid=savedInstanceState.getBoolean(NO_VID);
            step=savedInstanceState.getParcelable(STEP);
            videoUrl=savedInstanceState.getString(URL);
        }

        imageView = rootView.findViewById(R.id.image_view_error);
        textView = rootView.findViewById(R.id.text_view_desc);
        mPlayerView=rootView.findViewById(R.id.playerView);
        if (step != null){
            videoUrl=step.getVideoURL();
            initializePlayer(videoUrl);
            textView.setText(step.getDescription());
        }
        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE && !isTablet) {
            imageView.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
            getActivity().getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE);
        }



        if (noVid){
            mPlayerView.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
        }


        return rootView;
    }

    private void initializePlayer(String url) {
        if (url.equals("")) {
            mPlayerView.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            noVid=true;

        }

    else {
        if (mExoPlayer == null) {
            noVid=false;

            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            MediaSource mediaSource = new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory("exoplayer-codelab"))
                    .createMediaSource(Uri.parse(url));
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);


        }

    }

}
    private void releasePlayer() {
        if (mExoPlayer!=null){
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }

    }
    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }
    public void setStep(Steps step){
        this.step=step;
    }

    @Override
    public void onResume() {
        super.onResume();
        initializePlayer(videoUrl);

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(NO_VID,noVid);
        outState.putParcelable(STEP,step);
        outState.putString(URL,step.getVideoURL());
    }
}
