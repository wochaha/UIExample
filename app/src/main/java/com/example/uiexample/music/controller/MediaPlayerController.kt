package com.example.uiexample.music.controller

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.SeekBar
import com.example.uiexample.music.*
import com.example.uiexample.music.utils.Playing
import java.util.*

const val TAG = "MediaPlayerController"
const val UPDATE_SEEK = 1

class MediaPlayerController(
    private val seekBar: SeekBar
) {

    init {
        val seekBarChangeListener: SeekBar.OnSeekBarChangeListener = object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                seekBar?.progress = progress
            }

            /**
             * 暂停音乐
             * */
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                pause()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                val progress = seekBar?.progress
                val mSec = (((progress ?: 0).toFloat()) / 100)* Playing.duration
                mediaPlayer.seekTo(mSec.toInt())
                reStart()
            }

        }
        seekBar.setOnSeekBarChangeListener(seekBarChangeListener)
    }

    private val uiHandler = Handler(Looper.getMainLooper()) { msg ->
        val handle:Boolean = when(msg.what) {
            UPDATE_SEEK -> {
                updateSeekBar()
            }
            else -> false
        }
        handle
    }

    private val timer: Timer = Timer()
    private var scheduleTask: MutableTimerTask? = null

    private fun scheduleTimerTask() {
        scheduleTask = MutableTimerTask()
        val period = if (Playing.duration != -1 || Playing.duration != 0) {
            (Playing.duration / 100).toLong()
        } else {
            1000L
        }
        timer.schedule(scheduleTask, 0L, period)
    }

    private val preparedListener: MediaPlayer.OnPreparedListener = MediaPlayer.OnPreparedListener { mp ->
        mp ?: return@OnPreparedListener
        Playing.duration = mp.duration
        seekTo(0)
        mp.start()
        scheduleTimerTask()
    }

    private val mediaPlayer: MediaPlayer by lazy {
        MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
            setOnPreparedListener(preparedListener)
        }
    }

    public fun play(music: MusicItemData) {
        //同一首歌不切换
        if (Playing.music == music) return
        reset()
        Playing.music = music
        play()
    }

    private fun play() {
        val music = Playing.music
        music ?: return
        when (music.type) {
            MUSIC_FROM_RES -> {
                mediaPlayer.setDataSource((music as MusicItemDataFromRes).afd)
            }
            MUSIC_FROM_SD -> {
                mediaPlayer.setDataSource((music as MusicItemDataFromSD).path)
            }
        }
        mediaPlayer.prepareAsync()
    }

    public fun reStart() {
        if (isPlaying()) return
        mediaPlayer.start()
        scheduleTask?.let { task ->
            synchronized(task) {
                task.runnable = true
                synchronized(task.obLock) {
                    task.obLock.notifyAll()
                }
            }
        }
    }

    public fun pause() {
        if (!isPlaying()) return
        mediaPlayer.pause()
        scheduleTask?.let { task ->
            synchronized(task) {
                task.runnable = false
            }
        }
    }

    public fun stop() {
        reset()
    }

    //根据音乐进度调整进度条
    public fun seekTo(mSec: Int) {
        mediaPlayer.seekTo(mSec)
        if (!(Playing.duration == 0
                    || Playing.duration == -1)
        ) {
            val progress = (mSec * 100) / Playing.duration
            seekBar.progress = progress
        }
    }

    public fun isPlaying(): Boolean {
        return mediaPlayer.isPlaying
    }

    //不论是否在正在播放都会清除所有播放相关数据
    private fun reset() {
        seekTo(0)
        mediaPlayer.stop()
        mediaPlayer.reset()
        Playing.clear()
        scheduleTask?.cancel()
        scheduleTask = null
    }

    public fun destroy() {
        reset()
        mediaPlayer.release()
        timer.cancel()
    }

    private fun updateSeekBar(): Boolean {
        val p = seekBar.progress
        Log.d(TAG, "progress : $p")
        if (p == 100) {
            return false
        }
        seekBar.progress = p + 1
        return true
    }

    private inner class MutableTimerTask() : TimerTask() {
        @Volatile
        public var runnable: Boolean = true

        public val obLock = Object()

        override fun run() {
            synchronized(obLock) {
                while (!runnable) {
                    try {
                        obLock.wait()
                    } catch (e : InterruptedException) {
                        Thread.interrupted()
                    }
                }
            }
            uiHandler.sendEmptyMessage(UPDATE_SEEK)
        }

    }
}
