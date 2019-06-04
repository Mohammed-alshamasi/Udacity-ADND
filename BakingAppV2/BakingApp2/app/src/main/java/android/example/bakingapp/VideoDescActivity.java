package android.example.bakingapp;

import android.content.Intent;
import android.example.bakingapp.Data.Steps;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toolbar;

import static android.example.bakingapp.ShortDescAdapter.KEY_STEP;
import static android.example.bakingapp.VideoFragment.created;

public class VideoDescActivity extends AppCompatActivity {
    Steps step;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_desc);

        Intent intent =getIntent();
        if (savedInstanceState!=null){
            step=savedInstanceState.getParcelable(KEY_STEP);
            created=savedInstanceState.getBoolean("created");
        }else{
            step=intent.getExtras().getParcelable(KEY_STEP);
        }
        VideoFragment videoFragment=new VideoFragment();
        videoFragment.setStep(step);

        FragmentManager fragmentManager =getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.frame_layout, videoFragment)
                    .commit();



    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("step",step);
        outState.putBoolean("created",created);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
