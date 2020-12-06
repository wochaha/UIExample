package com.example.uiexample.gallery

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class GalleryMetaData(
    @DrawableRes val resId: Int,
    @StringRes val decoration: Int
)