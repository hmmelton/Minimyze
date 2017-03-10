package com.sonora.android.fragments;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sonora.android.R;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by harrisonmelton on 1/30/17.
 * This class controls the content on the feed (home) page.
 */

public class FeedFragment extends Fragment {

    @SuppressWarnings("unused")
    private final String TAG = getClass().getSimpleName();

    /* Views */
    @BindView(R.id.feed_action_button)
    FloatingActionButton mFAB;

    /* Colors */
    @BindColor(R.color.colorPrimary)
    int PRIMARY_COLOR;
    @BindColor(R.color.colorPrimaryDark)
    int PRIMARY_COLOR_DARK;

    /* Actions */

    // FAB OnClick handler
    @OnClick(R.id.feed_action_button)
    void onFabClick() {
        // TODO: create and upload new recipe
    }

    public static FeedFragment newInstance() {
        return new FeedFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate fragment's layout
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        ButterKnife.bind(this, view);

        // Set FAB colors
        mFAB.setBackgroundTintList(ColorStateList.valueOf(PRIMARY_COLOR));
        mFAB.setRippleColor(PRIMARY_COLOR_DARK);

        return view;
    }
}
