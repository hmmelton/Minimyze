package com.sonora.android.custom;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by harrisonmelton on 2/6/17.
 * This is a custom implementation of GlideModule, used to set configuration for the library.
 */

public class GlideConfiguration implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        // Forge Glide to use ARGB 8888 format
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
    }

    @Override
    public void registerComponents(Context context, Glide glide) {}
}
