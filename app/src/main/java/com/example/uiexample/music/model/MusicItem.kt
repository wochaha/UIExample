package com.example.uiexample.music

import android.content.res.AssetFileDescriptor
import android.net.Uri
import android.view.View
import androidx.annotation.IntDef
import androidx.annotation.RawRes
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_music.view.*

@IntDef(
    MUSIC_FROM_RES,
    MUSIC_FROM_SD
)
@Retention(AnnotationRetention.SOURCE)
annotation class ContentType

const val MUSIC_FROM_RES = 1
const val MUSIC_FROM_SD = 2

public class MusicItemViewHolder(
    private val view: View
) : RecyclerView.ViewHolder(view) {

    public fun bindView(data: MusicItemData, action:(MusicItemData) -> Unit) {
        itemView.music_title.text = data.title
        itemView.music_artist.text = data.singer
        val second = data.duration / (1000)
        itemView.music_duration.text = timeFormat(second)
        val size = data.size.toFloat() / (1024 * 1024)
        itemView.music_size.text = String.format("%.2f M",size)
        itemView.setOnClickListener {
            action(data)
        }
    }

    private fun timeFormat(totalSecond: Int): String {
        var minutes: Int
        var seconds = 0
        var hours: Int
        return when {
            totalSecond >= 3600 -> {
                hours = totalSecond / 3600
                minutes = (totalSecond - hours * 3600) / 60
                seconds = (totalSecond - hours * 3600 - minutes * 60)
                String.format("%d:%02d%02d", hours, minutes, seconds)
            }
            totalSecond in 60..3599 -> {
                minutes = totalSecond / 60
                seconds = (totalSecond - minutes * 60)
                String.format("%2d:%2d", minutes, seconds)
            }
            else -> {
                String.format("%2d", seconds)
            }
        }
    }

}

public open class MusicItemData(
    @ContentType val type: Int = MUSIC_FROM_RES,
) {
    var title = "未知音乐"
    var duration: Int = -1
    var singer: String = "未知音乐人"
    var size: Long = 0L
}

public class MusicItemDataFromRes(
    val afd: AssetFileDescriptor
    ) : MusicItemData(type = MUSIC_FROM_RES) {

    }

public class MusicItemDataFromSD(
    val path: String
) : MusicItemData(type = MUSIC_FROM_SD) {

}