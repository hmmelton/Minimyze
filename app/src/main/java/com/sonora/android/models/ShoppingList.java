package com.sonora.android.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by harrisonmelton on 2/4/17.
 * This class is a model for a shopping list.
 */

public class ShoppingList {

    // Sub-lists
    private List<Ingredient> mBeverages;
    private List<Ingredient> mBakery;
    private List<Ingredient> mCannedAndJarred;
    private List<Ingredient> mDairy;
    private List<Ingredient> mDryAndBaking;
    private List<Ingredient> mFrozen;
    private List<Ingredient> mMeats;
    private List<Ingredient> mProduce;
    private List<Ingredient> mSeafood;
    private List<Ingredient> mSnacks;

    // Empty constructor required by Firebase
    public ShoppingList() {}

    /**
     * Constructor
     * @param json JSON Object holding information for an entire shopping list
     */
    public ShoppingList(JSONObject json) {
        try {
            this.mBeverages = parseJSON(json.getJSONArray("beverages"));
            this.mBakery = parseJSON(json.getJSONArray("bakery"));
            this.mCannedAndJarred = parseJSON(json.getJSONArray("canned_and_jarred"));
            this.mDairy = parseJSON(json.getJSONArray("dairy"));
            this.mDryAndBaking = parseJSON(json.getJSONArray("dry_and_baking"));
            this.mFrozen = parseJSON(json.getJSONArray("frozen"));
            this.mMeats = parseJSON(json.getJSONArray("meats"));
            this.mProduce = parseJSON(json.getJSONArray("produce"));
            this.mSeafood = parseJSON(json.getJSONArray("seafood"));
            this.mSnacks = parseJSON(json.getJSONArray("snacks"));
        } catch (JSONException e) {
            // There was a problem parsing the JSON object
            e.printStackTrace();
        }
    }

    /**
     * This method parses over a given array of items, returning them as a List of Ingredient.
     * @param subList JSONArray to be parsed
     * @return List of objects in passed array, converted to ListItems
     */
    private List<Ingredient> parseJSON(JSONArray subList) {
        List<Ingredient> result = new ArrayList<>();
        for (int i = 0; i < subList.length(); i++) {
            try {
                result.add(new Ingredient(subList.getJSONObject(i)));
            } catch (JSONException e) {
                // Problem with JSON parsing
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * This method gives the number of items in the shopping list.
     * @return number of total items in shopping list
     */
    public int getCount() {
        return mBeverages.size() + mBakery.size() + mCannedAndJarred.size() + mDairy.size()
                + mDryAndBaking.size() + mFrozen.size() + mMeats.size() + mProduce.size()
                + mSeafood.size() + mSnacks.size();
    }

    // Getter methods

    public List<Ingredient> getBakery() {
        return mBakery;
    }

    public List<Ingredient> getBeverages() {
        return mBeverages;
    }

    public List<Ingredient> getCannedAndJarred() {
        return mCannedAndJarred;
    }

    public List<Ingredient> getDairy() {
        return mDairy;
    }

    public List<Ingredient> getDryAndBaking() {
        return mDryAndBaking;
    }

    public List<Ingredient> getFrozen() {
        return mFrozen;
    }

    public List<Ingredient> getMeats() {
        return mMeats;
    }

    public List<Ingredient> getProduce() {
        return mProduce;
    }

    public List<Ingredient> getSeafood() {
        return mSeafood;
    }

    public List<Ingredient> getSnacks() {
        return mSnacks;
    }
}
