package android.example.bakingapp;

import android.example.bakingapp.Data.Recipe;
import android.example.bakingapp.Network.RecipeService;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class MasterFragment extends Fragment {
    private RecyclerView recyclerView;
    ArrayList<Recipe> recipes=new ArrayList<>();
    ArrayList<Recipe> recipes2=new ArrayList<>();
    MasterListAdapter masterListAdapter;
    final String KEY_ARR="array";
    final String KEY_RECY="recycle";
   private Parcelable savedRecycler;
    public MasterFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.master_list_fragment, container, false);

        rootView.setBackgroundColor(Color.WHITE);
        recyclerView=rootView.findViewById(R.id.recycler_view_main);

        if (savedInstanceState!=null){
            recipes2 = savedInstanceState.getParcelableArrayList(KEY_ARR);
            savedRecycler=savedInstanceState.getParcelable(KEY_RECY);
            masterListAdapter = new MasterListAdapter(getContext(), recipes2);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(masterListAdapter);
        }
        else {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            final RecipeService recipeService = retrofit.create(RecipeService.class);
            Call<ArrayList<Recipe>> call = recipeService.getRecipes();
            call.enqueue(new Callback<ArrayList<Recipe>>() {

                @Override

                public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                    if (!response.isSuccessful()) {
                        return;
                    }
                    recipes = response.body();

                if (savedInstanceState==null){
                    if (recipes != null) {
                        recipes2.addAll(recipes);
                        masterListAdapter = new MasterListAdapter(getContext(), recipes);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(masterListAdapter);

                    }
                }


                }

                @Override
                public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                }
            });
        }

        return rootView;
    }

    public void onPause() {
        super.onPause();
    }

    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();

    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(KEY_ARR,recipes);
    }
}
