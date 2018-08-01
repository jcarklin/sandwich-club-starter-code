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

            JSONObject jsonObject = sandwichDetailsJsonObject.getJSONObject("name");
            String mainName = jsonObject.getString("mainName");
            JSONArray jsonArray = jsonObject.getJSONArray("alsoKnownAs");
            List<String> alsoKnownAsList = new ArrayList<>();
            for (int i=0; i<jsonArray.length();i++) {
                alsoKnownAsList.add(jsonArray.getString(i));
            }
            String placeOfOrigin = sandwichDetailsJsonObject.getString("placeOfOrigin");
            String description = sandwichDetailsJsonObject.getString("description");
            String image = sandwichDetailsJsonObject.getString("image");
            jsonArray = sandwichDetailsJsonObject.getJSONArray("ingredients");
            List<String> ingredientsList = new ArrayList<>();
            for (int i=0; i<jsonArray.length();i++) {
                ingredientsList.add(jsonArray.getString(i));
            }
            return new Sandwich(mainName, alsoKnownAsList, placeOfOrigin, description, image, ingredientsList);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}