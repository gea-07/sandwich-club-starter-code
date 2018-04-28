package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static final String JSON_NAME_KEY = "name";
    public static final String JSON_MAIN_NAME_KEY = "mainName";
    public static final String JSON_ALSO_KNOWN_AS_KEY = "alsoKnownAs";
    public static final String JSON_PLACE_OF_ORIGIN_KEY = "placeOfOrigin";
    public static final String JSON_DESCRIPTION_KEY = "description";
    public static final String JSON_IMAGE_KEY = "image";
    public static final String JSON_INGREDIENTS_KEY = "ingredients";

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
            mainName = sandwich.optJSONObject(JSON_NAME_KEY).optString(JSON_MAIN_NAME_KEY);

            // get alsoKnownAs
            JSONArray alsoKnown = sandwich.optJSONObject(JSON_NAME_KEY).optJSONArray(JSON_ALSO_KNOWN_AS_KEY);

            if (alsoKnown != null) {
                for (int i = 0; i < alsoKnown.length(); i++) {
                    alsoKnownList.add(alsoKnown.optString(i));
                }
            }

            // get placeOfOrigin
            placeOfOrigin = sandwich.optString(JSON_PLACE_OF_ORIGIN_KEY);

            // get description
            description = sandwich.optString(JSON_DESCRIPTION_KEY);

            // get string image
            image = sandwich.optString(JSON_IMAGE_KEY);

            // get ingredients
            JSONArray ingredients = sandwich.optJSONArray(JSON_INGREDIENTS_KEY);

            if (ingredients != null) {
                for (int i = 0; i < ingredients.length(); i++) {
                    ingredientsList.add(ingredients.optString(i));
                }
            }

        }
        catch (Throwable e) {
            e.printStackTrace();
        }

        return new Sandwich(mainName, alsoKnownList, placeOfOrigin, description, image, ingredientsList);
    }
}
