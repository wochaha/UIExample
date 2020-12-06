package com.example.uiexample.music.controller

import android.media.AudioManager
import android.util.Log
import android.widget.SeekBar

class VolumeController(
    private val audioManager: AudioManager,
    private val seekBar: SeekBar
) {
    private val maxVolume: Int = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
    private var currentVolume: Int = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)

    init {
        seekBar.max = maxVolume
        seekBar.progress = currentVolume

        Log.d(TAG, "max volume: $maxVolume")
        Log.d(TAG, "current volume: $currentVolume")

        seekBar.progress = currentVolume

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                seekBar ?: return
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE)
                seekBar.progress = progress
                currentVolume = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
    }

    public fun upMusicVolume() {
        changeMusicVolume(currentVolume + 1)
    }

    public fun downMusicVolume() {
        changeMusicVolume(currentVolume - 1)
    }

    private fun changeMusicVolume(volume: Int) {
        if (volume > maxVolume) return
        else if (volume < 0) return
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE)
        currentVolume = volume
        seekBar.progress = volume
        Log.d(TAG, "current volume: $currentVolume")
    }
}