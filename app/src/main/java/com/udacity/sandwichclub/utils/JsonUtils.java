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
            String mainName="", description = "", placeOfOrigin = "", image="";
            List<String> alsoKnownAsList=new ArrayList<>(), ingredientsList = new ArrayList<>();
            JSONObject sandwichDetailsJsonObject = new JSONObject(json);
            if (sandwichDetailsJsonObject != null) {
                JSONObject jsonObject = sandwichDetailsJsonObject.optJSONObject("name");
                if (jsonObject != null) {
                    mainName = jsonObject.optString("mainName", "");
                }
                JSONArray jsonArray = jsonObject.optJSONArray("alsoKnownAs");
                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        alsoKnownAsList.add(jsonArray.optString(i));
                    }
                }
                placeOfOrigin = sandwichDetailsJsonObject.optString("placeOfOrigin","");
                description = sandwichDetailsJsonObject.optString("description","");
                image = sandwichDetailsJsonObject.optString("image","");
                jsonArray = sandwichDetailsJsonObject.optJSONArray("ingredients");
                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        ingredientsList.add(jsonArray.optString(i));
                    }
                }
            }
            return new Sandwich(mainName, alsoKnownAsList, placeOfOrigin, description, image, ingredientsList);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}