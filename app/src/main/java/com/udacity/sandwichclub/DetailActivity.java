package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @BindView(R.id.origin_tv)
    TextView originIv;

    @BindView(R.id.also_known_tv)
    TextView alsoKnownTv;

    @BindView(R.id.ingredients_tv)
    TextView ingredientsTv;

    @BindView(R.id.description_tv)
    TextView descriptionTv;

    private  Sandwich selectedSandwich=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

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
        selectedSandwich = JsonUtils.parseSandwichJson(json);

        if (selectedSandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(selectedSandwich.getImage())
                .into(ingredientsIv);

        setTitle(selectedSandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

        if (isNullOrEmpty(selectedSandwich.getPlaceOfOrigin())) {
            ((ViewGroup)findViewById(R.id.origin_ll).getParent()).removeView(findViewById(R.id.origin_ll));
        } else {
            originIv.setText(selectedSandwich.getPlaceOfOrigin());
        }

        if (isNullOrEmpty(getListAsCsv(selectedSandwich.getAlsoKnownAs()))) {
            ((ViewGroup)findViewById(R.id.also_known_ll).getParent()).removeView(findViewById(R.id.also_known_ll));
        } else {
            alsoKnownTv.setText(getListAsCsv(selectedSandwich.getAlsoKnownAs()));
        }

        if (isNullOrEmpty(getListAsCsv(selectedSandwich.getIngredients()))) {
            ((ViewGroup)findViewById(R.id.ingredients_ll).getParent()).removeView(findViewById(R.id.ingredients_ll));
        } else {
            ingredientsTv.setText(getListAsCsv(selectedSandwich.getIngredients()));
        }

        if (isNullOrEmpty(selectedSandwich.getDescription())) {
            ((ViewGroup)findViewById(R.id.description_ll).getParent()).removeView(findViewById(R.id.description_ll));
        } else {
            descriptionTv.setText(selectedSandwich.getDescription());
        }
    }

    private String getListAsCsv(List<String> stringList) {
        String csvString = "";
        if (stringList != null) {
            StringBuilder builder = new StringBuilder("");
            boolean addComma = false;
            for (String listItem : stringList) {
                 if (addComma) {
                     builder.append(", ");
                 }
                 addComma = true;
                 builder.append(listItem);
             }
             csvString = builder.toString();
        }
        return csvString;
    }

    private boolean isNullOrEmpty(String stringToCheck) {
        return  (stringToCheck == null || stringToCheck.trim().isEmpty());
    }
}
