package android.example.bakingapp;

import android.content.Intent;
import android.example.bakingapp.Data.Ingredients;
import android.example.bakingapp.Data.Recipe;
import android.example.bakingapp.Data.Steps;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import static android.example.bakingapp.MasterListAdapter.KEY_DETAILS;
import static android.example.bakingapp.MasterListAdapter.KEY_ING;
import static android.example.bakingapp.MasterListAdapter.KEY_REC;

public class DetailActivity extends AppCompatActivity {
    public static boolean isTablet=false;
    ArrayList<Steps>steps;
    Recipe recipe=null;
    ArrayList<Ingredients> ingredients;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().hide();
        if (savedInstanceState!=null){
            steps=new ArrayList<>();
            steps.addAll(savedInstanceState.getParcelableArrayList(KEY_DETAILS));
            recipe=savedInstanceState.getParcelable(KEY_REC);
            ingredients=new ArrayList<>();
            ingredients=savedInstanceState.getParcelableArrayList(KEY_ING);
        }
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();

        DetailFragment detailFragment=new DetailFragment();

        if (bundle!=null){
            steps=new ArrayList<>();
            steps.addAll(bundle.getParcelableArrayList(KEY_DETAILS));
            recipe=bundle.getParcelable(KEY_REC);
            ingredients=new ArrayList<>();
            ingredients=bundle.getParcelableArrayList(KEY_ING);
            detailFragment.setSteps(steps);
            detailFragment.setRecipe(recipe);
            detailFragment.setIngredients(ingredients);

        }
        FragmentManager fragmentManager=getSupportFragmentManager();



        if (findViewById(R.id.tablet)!=null){
            isTablet=true;
            this.getSupportActionBar().hide();
            VideoFragment videoFragment=new VideoFragment();
            ArrayList<Steps> stepsArrayList=bundle.getParcelableArrayList(KEY_DETAILS);
            videoFragment.setStep(stepsArrayList.get(0));
            fragmentManager.beginTransaction().add(R.id.frame_container_detail_tablet,detailFragment).commit();
            fragmentManager.beginTransaction().add(R.id.frame_vid,videoFragment).commit();

        }
        else{
            isTablet=false;
            fragmentManager.beginTransaction()
                    .add(R.id.frame_container_detail,detailFragment)
                    .commit();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (findViewById(R.id.tablet)!=null){
            isTablet=true;
            this.getSupportActionBar().hide();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_DETAILS,steps);
        outState.putParcelableArrayList(KEY_ING,ingredients);
        outState.putParcelable(KEY_REC,recipe);
    }
}
