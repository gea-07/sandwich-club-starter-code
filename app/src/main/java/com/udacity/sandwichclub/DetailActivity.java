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

import org.json.JSONException;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private ImageView mImageView;
    private TextView mAlsoKnownAsTextView;
    private TextView mIngredientsTextView;
    private TextView mPlaceOfOriginTextView;
    private TextView mDescriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mImageView = (ImageView)findViewById(R.id.image_iv);
        mAlsoKnownAsTextView = (TextView)findViewById(R.id.also_known_tv);
        mIngredientsTextView = (TextView)findViewById(R.id.ingredients_tv);
        mPlaceOfOriginTextView = (TextView)findViewById(R.id.origin_tv);
        mDescriptionTextView = (TextView)findViewById(R.id.description_tv);


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
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        setTitle(sandwich.getMainName());

        for (int i = 0; i < sandwich.getAlsoKnownAs().size(); i++) {
            mAlsoKnownAsTextView.append(sandwich.getAlsoKnownAs().get(i) + "\n");
        }

        for (int i = 0; i < sandwich.getIngredients().size(); i++) {
            mIngredientsTextView.append(sandwich.getIngredients().get(i) + "\n");
        }

        mPlaceOfOriginTextView.setText(sandwich.getPlaceOfOrigin());

        mDescriptionTextView.setText(sandwich.getDescription());

        Picasso.with(this)
                .load(sandwich.getImage())
                .into(mImageView);

    }
}
