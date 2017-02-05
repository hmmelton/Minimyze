package com.sonora.android.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sonora.android.R;

/**
 * Created by harrisonmelton on 1/30/17.
 * This class controls the content on the feed (home) page.
 */

public class FeedFragment extends Fragment {

    public static FeedFragment newInstance() {
        return new FeedFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate fragment's layout
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }
}
