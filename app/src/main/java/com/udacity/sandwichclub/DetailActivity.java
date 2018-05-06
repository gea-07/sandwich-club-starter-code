package com.udacity.sandwichclub;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;
import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @BindView(R.id.image_iv)
    ImageView mImageView;

    @BindView(R.id.also_known_label_tv)
    TextView mAlsoKnownAsLabelTextView;

    @BindView(R.id.also_known_tv)
    TextView mAlsoKnownAsTextView;

    @BindView(R.id.ingredients_label_tv)
    TextView mIngredientsLabelTextView;

    @BindView(R.id.ingredients_tv)
    TextView mIngredientsTextView;

    @BindView(R.id.origin_label_tv)
    TextView mPlaceOfOriginLabelTextView;

    @BindView(R.id.origin_tv)
    TextView mPlaceOfOriginTextView;

    @BindView(R.id.description_label_tv)
    TextView mDescriptionLabelTextView;

    @BindView(R.id.description_tv)
    TextView mDescriptionTextView;

    @BindView(R.id.image_label_tv)
    TextView mImageLabelTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

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

        if (!sandwich.getMainName().isEmpty()) {
            setTitle(sandwich.getMainName());
            mImageLabelTextView.setText(sandwich.getMainName());
        }

        if (!sandwich.getAlsoKnownAs().isEmpty()) {
            mAlsoKnownAsLabelTextView.setVisibility(View.VISIBLE);
            mAlsoKnownAsTextView.setVisibility(View.VISIBLE);
            mAlsoKnownAsTextView.setText(TextUtils.join("\n", sandwich.getAlsoKnownAs()));
        }
        else {
            mAlsoKnownAsLabelTextView.setVisibility(View.GONE);
            mAlsoKnownAsTextView.setVisibility(View.GONE);
        }

        if (!sandwich.getIngredients().isEmpty()) {
            mIngredientsLabelTextView.setVisibility(View.VISIBLE);
            mIngredientsTextView.setVisibility(View.VISIBLE);
            mIngredientsTextView.setText(TextUtils.join("\n", sandwich.getIngredients()));
        }
        else {
            mIngredientsLabelTextView.setVisibility(View.GONE);
            mIngredientsTextView.setVisibility(View.GONE);
        }

        if (!sandwich.getPlaceOfOrigin().isEmpty()) {
            mPlaceOfOriginLabelTextView.setVisibility(View.VISIBLE);
            mPlaceOfOriginTextView.setVisibility(View.VISIBLE);
            mPlaceOfOriginTextView.setText(sandwich.getPlaceOfOrigin());
        }
        else {
            mPlaceOfOriginLabelTextView.setVisibility(View.GONE);
            mPlaceOfOriginTextView.setVisibility(View.GONE);
        }

        if (!sandwich.getDescription().isEmpty()) {
            mDescriptionLabelTextView.setVisibility(View.VISIBLE);
            mDescriptionTextView.setVisibility(View.VISIBLE);
            mDescriptionTextView.setText(sandwich.getDescription());
        }
        else
        {
            mDescriptionLabelTextView.setVisibility(View.GONE);
            mDescriptionTextView.setVisibility(View.GONE);
        }

        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.drawable.placeholder)
                .into(mImageView);

    }
}
