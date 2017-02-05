package com.sonora.android.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    public ShoppingList(JSONArray json) {

    }

    private class ListItem {

        private int count;
        private String itemName, countType;

        protected ListItem(JSONObject json) {
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

    /**
     * This method gives the number of items in the shopping list.
     * @return number of total items in shopping list
     */
    public int getCount() {
        return mBeverages.size() + mBakery.size() + mCannedAndJarred.size() + mDairy.size()
                + mDryAndBaking.size() + mFrozen.size() + mMeats.size() + mProduce.size()
                + mSeafood.size() + mSnacks.size();
    }
}
