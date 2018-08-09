package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        try {

            JSONObject sandwichDetailsJsonObject = new JSONObject(json);

            JSONObject jsonObject = sandwichDetailsJsonObject.optJSONObject("name");
            String mainName = jsonObject.optString("mainName");
            JSONArray jsonArray = jsonObject.optJSONArray("alsoKnownAs");
            List<String> alsoKnownAsList = new ArrayList<>();
            for (int i=0; i<jsonArray.length();i++) {
                alsoKnownAsList.add(jsonArray.optString(i));
            }
            String placeOfOrigin = sandwichDetailsJsonObject.optString("placeOfOrigin");
            String description = sandwichDetailsJsonObject.optString("description");
            String image = sandwichDetailsJsonObject.optString("image");
            jsonArray = sandwichDetailsJsonObject.optJSONArray("ingredients");
            List<String> ingredientsList = new ArrayList<>();
            for (int i=0; i<jsonArray.length();i++) {
                ingredientsList.add(jsonArray.optString(i));
            }
            return new Sandwich(mainName, alsoKnownAsList, placeOfOrigin, description, image, ingredientsList);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}