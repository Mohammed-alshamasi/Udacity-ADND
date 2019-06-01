package android.example.bakingapp;

import android.content.Intent;
import android.example.bakingapp.Data.Ingredients;
import android.example.bakingapp.Data.Recipe;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import static android.example.bakingapp.MasterListAdapter.KEY_ING;
import static android.example.bakingapp.MasterListAdapter.KEY_REC;

public class ingredientsActivity extends AppCompatActivity {
    TextView textView;
    Recipe recipe;
    ArrayList<Ingredients> ingredients=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);
        textView=findViewById(R.id.text_view_ingredients);

            textView.setVisibility(View.GONE);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();

        if (bundle!=null){
            recipe=bundle.getParcelable(KEY_REC);
            ingredients.addAll(bundle.<Ingredients>getParcelableArrayList(KEY_ING));
        }
        for (int i =0; i<ingredients.size()-1;i++){
            if (i==ingredients.size()-2){
                textView.append(ingredients.get(i).getQuantity()+" "+ingredients.get(i).getMeasure()+" "+ingredients.get(i).getIngredient());
            }
            else
                textView.append(ingredients.get(i).getQuantity()+" "+ingredients.get(i).getMeasure()+" "+ingredients.get(i).getIngredient()+System.lineSeparator()+System.lineSeparator());

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}

