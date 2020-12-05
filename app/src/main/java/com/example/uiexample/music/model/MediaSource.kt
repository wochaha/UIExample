package com.example.uiexample.music.model

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.uiexample.music.MusicItemData
import com.example.uiexample.music.MusicItemDataFromRes
import com.example.uiexample.music.MusicItemDataFromSD
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

const val PREFIX_PATH = "media/"

class MediaSource(
    private val context: Context
) {
    suspend fun loadMediaResourceFromAssets() : List<MusicItemData> {
        val assetManager = context.assets
        val result = mutableListOf<MusicItemData>()
        withContext(Dispatchers.IO) {
            val all = assetManager.list("media")
            if (all == null || all.isEmpty()) {
                assetManager.close()
                return@withContext
            }
            all.forEach { name ->
                val afd = assetManager.openFd(PREFIX_PATH + name)
                result.add(MusicItemDataFromRes(afd).apply {
                    title = name
                    size = afd.declaredLength
                })
            }
        }
        return result
    }


    suspend fun loadMediaResourceFromSDCard() : List<MusicItemData> {
        val result = mutableListOf<MusicItemData>()
        val cursor = context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            MediaStore.Audio.Media.DEFAULT_SORT_ORDER
        )
        cursor ?: return result
        withContext(Dispatchers.IO) {
            //todo 加载sd卡内的音乐
            while (cursor.moveToNext() and (result.size < 30)) {
                val name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME))
                val singer = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST))
                } else {
                    "未知音乐人"
                }
                val path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA))
                val duration = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION))
                } else {
                    -1
                }
                val size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE))
                result.add(
                    MusicItemDataFromSD(path).apply {
                        title = name
                        this.singer = singer
                        this.duration = duration
                        this.size = size
                    }
                )
            }
        }
        cursor.close()
        return result
    }
}