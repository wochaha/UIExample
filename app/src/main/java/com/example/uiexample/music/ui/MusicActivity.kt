package com.example.uiexample.music.ui

import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import androidx.lifecycle.ViewModelProvider
import com.example.uiexample.R
import com.example.uiexample.BaseActivity
import com.example.uiexample.music.utils.Playing
import com.example.uiexample.music.controller.MediaPlayerController
import com.example.uiexample.music.controller.VolumeController
import com.example.uiexample.music.model.MusicViewModel
import kotlinx.android.synthetic.main.activity_music.*

class MusicActivity : BaseActivity() {
    private val viewModel : MusicViewModel by lazy {
        ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(MusicViewModel::class.java)
    }

    private val mediaPlayerController: MediaPlayerController by lazy {
        MediaPlayerController(music_seek_bar)
    }

    private lateinit var volumeController: VolumeController

    private val adapter: MusicAdapter by lazy {
        MusicAdapter(
            viewModel.musics.value ?: emptyList()
        ) { data ->
            val result = mediaPlayerController.play(data)
            if (result) changePlayText(result)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar.init("音乐播放器")
        initView()
        observe()
    }

    private fun initView() {
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        volumeController = VolumeController(audioManager, volume_seek_bar)

        music_list.adapter = adapter

        changePlayText(false)

        play.setOnClickListener {
            if (Playing.music == null && viewModel.firstMusic != null) {
                val result = mediaPlayerController.play(viewModel.firstMusic!!)
                changePlayText(result)
            } else {
                if (mediaPlayerController.isPlaying()) {
                    mediaPlayerController.pause()
                    changePlayText(false)
                } else {
                    mediaPlayerController.reStart()
                    changePlayText(true)
                }
            }
        }

        stop.setOnClickListener {
            mediaPlayerController.stop()
            play.text = resources.getString(R.string.play)
        }
    }

    private fun changePlayText(isPlaying: Boolean) {
        if (isPlaying) {
            play.text = resources.getString(R.string.pause)
        } else {
            play.text = resources.getString(R.string.play)
        }
    }

    private fun observe() {
        viewModel.musics.observe(this){ value ->
            adapter.updateContent(value)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return when(keyCode) {
            KeyEvent.KEYCODE_VOLUME_DOWN -> {
                volumeController.downMusicVolume()
                true
            }
            KeyEvent.KEYCODE_VOLUME_UP -> {
                volumeController.upMusicVolume()
                true
            }
            else -> {
                super.onKeyDown(keyCode, event)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayerController.destroy()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_music
    }

    override fun getContextMenuRes(): Int {
        return 0
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return false
    }
}