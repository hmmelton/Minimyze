package com.sonora.android.interfaces;

/**
 * Created by harrisonmelton on 3/22/17.
 * This interface is used as a callback for the completion of asynchronous functions.
 */

public interface OnCompleteListener {
    void complete(boolean result);
    void error(String message);
}
