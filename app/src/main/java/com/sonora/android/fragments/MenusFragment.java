package com.sonora.android.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sonora.android.R;
import com.sonora.android.interfaces.OnImageRetrievedListener;
import com.sonora.android.utils.FirebaseImageUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by harrisonmelton on 2/5/17.
 * This class controls the content on the menus page.
 */

public class MenusFragment extends Fragment {

    @BindView(R.id.image_view) protected ImageView mImageView;

    // Static method for constructing fragment
    public static MenusFragment newInstance() {
        return new MenusFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate layout's fragment
        View root = inflater.inflate(R.layout.fragment_menus, container, false);
        // Initialize Butter Knife
        ButterKnife.bind(this, root);

        FirebaseImageUtil.getImageReference(new OnImageRetrievedListener() {
            @Override
            public void onSuccess(Uri uri) {
                // URI fetched successfully, so load it into ImageView
                Glide.with(getContext())
                        .load(uri)
                        .into(mImageView);
            }

            @Override
            public void onFailure(Exception exception) {
                // URI was not fetched, so notify user
                Toast.makeText(getContext(), R.string.image_retrieval_error, Toast.LENGTH_LONG)
                        .show();
            }
        });

        return root;
    }
}
