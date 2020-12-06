package com.example.uiexample.gallery

import com.example.uiexample.R
import java.util.*

object DataCenter {
    private val galleries = mutableListOf<GalleryMetaData>()

    private var currentIndex: Int = 0

    init {
        galleries.apply {
            add(GalleryMetaData(R.drawable.res_f, R.string.d_f))
            add(GalleryMetaData(R.drawable.res_s, R.string.d_s))
            add(GalleryMetaData(R.drawable.res_t, R.string.d_t))
            add(GalleryMetaData(R.drawable.res_fo, R.string.d_fo))
            add(GalleryMetaData(R.drawable.res_fi, R.string.d_fi))
        }
    }

    public fun initLoad(): GalleryMetaData {
        return galleries[currentIndex]
    }

    public fun previous(): GalleryMetaData? {
        if (currentIndex == 0) {
            return null
        }
        currentIndex -= 1
        return galleries[currentIndex]
    }

    public fun next(): GalleryMetaData? {
        if (currentIndex == galleries.lastIndex) {
            return null
        }
        currentIndex += 1
        return galleries[currentIndex]
    }
}