package com.sonora.android.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by harrisonmelton on 3/3/17.
 * This is a model used to handle API POST responses.
 */

public class PostResponse {
    @SerializedName("success")
    String success;

    @SerializedName("error")
    String error;

    public boolean succeeded() {
        return success != null && success.length() > 0;
    }

    public boolean failed() {
        return error != null && error.length() > 0;
    }

    public String getSuccess() {
        return success;
    }

    public String getError() {
        return error;
    }
}
