package android.example.bakingapp;

import android.content.res.Configuration;
import android.example.bakingapp.Data.Steps;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.squareup.picasso.Picasso;

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
    static final String POS_KEY="pos";
    static final String READY_KEY="ready";
    String videoUrl=null;
    static long pos=C.TIME_UNSET;
    Boolean playWhenReady=false;
    int windowIndex=-1;
   static boolean created=false;

    public VideoFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.video_fragment, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);

        if (!isTablet)
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        if(savedInstanceState!=null){
            noVid=savedInstanceState.getBoolean(NO_VID);
            step=savedInstanceState.getParcelable(STEP);
            videoUrl=savedInstanceState.getString(URL);
            pos=savedInstanceState.getLong(POS_KEY);
            playWhenReady=savedInstanceState.getBoolean(READY_KEY);

        }


        imageView = rootView.findViewById(R.id.image_view_error);
        textView = rootView.findViewById(R.id.text_view_desc);
        mPlayerView=rootView.findViewById(R.id.playerView);

        if (step != null){
            videoUrl=step.getVideoURL();
            initializePlayer(videoUrl,pos);
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
            if (!TextUtils.isEmpty(step.getThumbnailURL()))
                Picasso.get().load(step.getThumbnailURL()).into(imageView);
            else
                imageView.setImageResource(R.drawable.error);
        }

        return rootView;
    }
    private void initializePlayer(String url,long p) {
        if (TextUtils.isEmpty(url)) {
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

            mExoPlayer.prepare(mediaSource,true,true);
            if (p != C.TIME_UNSET)
                mExoPlayer.seekTo(p);
            else
                mExoPlayer.seekTo(0);

            mExoPlayer.setPlayWhenReady(true);
        }

    }

}
    private void releasePlayer() {
        if (mExoPlayer!=null){
            pos=mExoPlayer.getCurrentPosition();
            playWhenReady=mExoPlayer.getPlayWhenReady();
            windowIndex=mExoPlayer.getCurrentWindowIndex();
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;

        }


    }
    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer!=null){
            releasePlayer();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
            releasePlayer();

    }
    public void setStep(Steps step){
        this.step=step;
    }



    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(NO_VID,noVid);
        outState.putParcelable(STEP,step);
        outState.putString(URL,step.getVideoURL());
        outState.putLong(POS_KEY,pos);
        outState.putBoolean(READY_KEY,playWhenReady);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        pos=-1;
    }
}
