package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView mTextViewAlsoKnownAs, mTextViewOrigin, mTextViewIngredients, mTextViewDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        mTextViewAlsoKnownAs = findViewById(R.id.also_known_tv);

        mTextViewIngredients = findViewById(R.id.ingredients_tv);

        mTextViewOrigin = findViewById(R.id.origin_tv);

        mTextViewDescription = findViewById(R.id.description_tv);

        mTextViewDescription = findViewById(R.id.description_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich s) {

        List<String> alsoKnownAsList = s.getAlsoKnownAs();

        for (int i = 0; i < alsoKnownAsList.size(); i++) {
            int k = alsoKnownAsList.size();
            mTextViewAlsoKnownAs.append(alsoKnownAsList.get(i));
            if (i != k - 1)
                mTextViewAlsoKnownAs.append(", ");
        }

        mTextViewOrigin.setText(s.getPlaceOfOrigin());

        List<String> ingredientsList = s.getIngredients();
        for (int i = 0; i < ingredientsList.size(); i++) {
            int k = ingredientsList.size();
            mTextViewIngredients.append(ingredientsList.get(i));
            if (i != k - 1)
                mTextViewIngredients.append(", ");
        }

        mTextViewDescription.setText(s.getDescription());


    }
}
