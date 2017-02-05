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
    private List<ListItem> mBeverages;
    private List<ListItem> mBakery;
    private List<ListItem> mCannedAndJarred;
    private List<ListItem> mDairy;
    private List<ListItem> mDryAndBaking;
    private List<ListItem> mFrozen;
    private List<ListItem> mMeats;
    private List<ListItem> mProduce;
    private List<ListItem> mSeafood;
    private List<ListItem> mSnacks;

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
     * This method parses over a given array of items, returning them as a List of ListItem.
     * @param subList JSONArray to be parsed
     * @return List of objects in passed array, converted to ListItems
     */
    private List<ListItem> parseJSON(JSONArray subList) {
        List<ListItem> result = new ArrayList<>();
        for (int i = 0; i < subList.length(); i++) {
            try {
                result.add(new ListItem(subList.getJSONObject(i)));
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

    public List<ListItem> getBakery() {
        return mBakery;
    }

    public List<ListItem> getBeverages() {
        return mBeverages;
    }

    public List<ListItem> getCannedAndJarred() {
        return mCannedAndJarred;
    }

    public List<ListItem> getDairy() {
        return mDairy;
    }

    public List<ListItem> getDryAndBaking() {
        return mDryAndBaking;
    }

    public List<ListItem> getFrozen() {
        return mFrozen;
    }

    public List<ListItem> getMeats() {
        return mMeats;
    }

    public List<ListItem> getProduce() {
        return mProduce;
    }

    public List<ListItem> getSeafood() {
        return mSeafood;
    }

    public List<ListItem> getSnacks() {
        return mSnacks;
    }

    /**
     * This class holds information for each item in a shopping list.
     */
    private class ListItem {

        private int count; // Number of item
        private String itemName, countType; // Name of item and count type (gallon, pound, etc.)

        /**
         * Constructor
         * @param json JSON Object holding information for the individual item
         */
        ListItem(JSONObject json) {
            try {
                this.count = json.getInt("count");
                this.itemName = json.getString("name");
                this.countType = json.getString("count_type");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Getter methods
        protected int getCount() {
            return count;
        }

        protected String getItemName() {
            return itemName;
        }

        protected String getCountType() {
            return countType;
        }
    }
}
