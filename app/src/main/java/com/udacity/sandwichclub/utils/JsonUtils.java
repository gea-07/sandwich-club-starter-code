package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        String mainName = "";
        List<String> alsoKnownList = new ArrayList<String>();
        String placeOfOrigin = "";
        String description = "";
        String image = "";
        List<String> ingredientsList = new ArrayList<String>();

        try {
            JSONObject sandwich = new JSONObject(json);

            if (sandwich == null){
                return null;
            }
            // get the main name
            mainName = sandwich.getJSONObject("name").getString("mainName");

            // get alsoKnownAs
            JSONArray alsoKnown = sandwich.getJSONObject("name").getJSONArray("alsoKnownAs");

            if (alsoKnown != null) {
                for (int i = 0; i < alsoKnown.length(); i++) {
                    alsoKnownList.add(alsoKnown.getString(i));
                }
            }

            // get placeOfOrigin
            placeOfOrigin = sandwich.getString("placeOfOrigin");

            // get description
            description = sandwich.getString("description");

            // get string image
            image = sandwich.getString("image");

            // get ingredients
            JSONArray ingredients = sandwich.getJSONArray("ingredients");

            if (ingredients != null) {
                for (int i = 0; i < ingredients.length(); i++) {
                    ingredientsList.add(ingredients.getString(i));
                }
            }

        }
        catch (Throwable e) {
            e.printStackTrace();
        }

        return new Sandwich(mainName, alsoKnownList, placeOfOrigin, description, image, ingredientsList);
    }
}
