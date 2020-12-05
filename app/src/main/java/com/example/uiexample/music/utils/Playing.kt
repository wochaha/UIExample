package com.example.uiexample.music.utils

import com.example.uiexample.music.MusicItemData


/**
 * 维护播放信息的单例类
 */
object Playing {
    var music: MusicItemData? = null
    var duration: Int = -1
        set(value) {
            if (music != null
                && (music?.duration == -1 || music?.duration == 0)
            ) {
                field = value
            }
        }

    public fun setPlayMusic(data: MusicItemData) {
        music = data
        duration = music?.duration ?: -1
    }

    public fun clear() {
        music = null
        duration = -1
    }
}