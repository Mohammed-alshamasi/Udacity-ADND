package android.example.bakingapp;

import android.content.Intent;
import android.example.bakingapp.Data.Ingredients;
import android.example.bakingapp.Data.Recipe;
import android.example.bakingapp.Data.Steps;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;



import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.example.bakingapp.MasterListAdapter.KEY_DETAILS;
import static android.example.bakingapp.MasterListAdapter.KEY_ING;
import static android.example.bakingapp.MasterListAdapter.KEY_REC;

public class DetailFragment extends Fragment {

    private RecyclerView recyclerView;
    ArrayList<Steps> steps=new ArrayList<>();
    ArrayList<Ingredients> ingredients=new ArrayList<>();
    private ImageView imageView;
   FloatingActionButton fab;
    Recipe recipe;
    String name;
    int id;

    public DetailFragment() {

    }

    public void setSteps(ArrayList<Steps> steps) {
        this.steps = steps;
    }
    public void setIngredients(ArrayList<Ingredients>ingredients){
        this.ingredients=ingredients;
    }
    public void setRecipe(Recipe recipe){
        this.recipe=recipe;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.detail_fragment_recycle, container, false);
        recyclerView = rootView.findViewById(R.id.recycler_view_detail);
        imageView=rootView.findViewById(R.id.image_view);
        fab=rootView.findViewById(R.id.fab);

        Bundle bundle=this.getArguments();
        Toolbar toolbar= rootView.findViewById(R.id.app_bar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.navigateUpFromSameTask(getActivity());
            }
        });
        if (bundle!=null){

         steps=bundle.getParcelableArrayList(KEY_DETAILS);
          recipe=bundle.getParcelable(KEY_REC);
          ingredients=bundle.getParcelableArrayList(KEY_ING);
        }
        if (savedInstanceState!=null){
            name=savedInstanceState.getString("name");
            id=savedInstanceState.getInt("id");
            ShortDescAdapter shortDescAdapter = new ShortDescAdapter(getContext(), steps);
            recyclerView.setAdapter(shortDescAdapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);

        }
        else{
            if (recipe!=null){
                name=recipe.getName();
                id =recipe.getId();
            }

            ShortDescAdapter shortDescAdapter = new ShortDescAdapter(getContext(), steps);
            recyclerView.setAdapter(shortDescAdapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putParcelableArrayList(KEY_ING,ingredients);
                bundle.putParcelable(KEY_REC,recipe);
                Intent intent =new Intent(getActivity(),ingredientsActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        CollapsingToolbarLayout collapsingToolbarLayout=rootView.findViewById(R.id.Collapsing_Toolbar_Layout);
        collapsingToolbarLayout.setTitle(name);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar_nut);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        switch (id){
            case 1:
                imageView.setImageResource(R.drawable.nutella_pie);
                break;
            case 2:
                imageView.setImageResource(R.drawable.brownies);
                break;
            case 3:
                imageView.setImageResource(R.drawable.yellow_cake);
                collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
                break;
            case 4:
                imageView.setImageResource(R.drawable.cheesecake);

                break;
                default:
                Picasso.get().load(R.drawable.ic_launcher_background).into(imageView);
        }
        return rootView;
    }
    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(name);
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("name",name);
        outState.putInt("id",id);
        outState.putParcelableArrayList("steps",steps);
    }

}
