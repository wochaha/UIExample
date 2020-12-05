package com.example.uiexample.music.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.uiexample.music.MusicItemData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MusicViewModel(application: Application) : AndroidViewModel(application) {
    val musics : MutableLiveData<MutableList<MusicItemData>> = MutableLiveData()

    private val serviceJob = SupervisorJob()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)

    private val mediaSource: MediaSource = MediaSource(getApplication())
    var firstMusic: MusicItemData? = null

    init {
        musics.value = mutableListOf()
        loadMusic()
    }

    private fun loadMusic() {
        serviceScope.launch {
            val resMusic = mediaSource.loadMediaResourceFromAssets()
            val sdMusic = mediaSource.loadMediaResourceFromSDCard()
            musics.value = (resMusic + sdMusic).toMutableList()
            firstMusic = musics.value?.get(0)
        }
    }

    override fun onCleared() {
        super.onCleared()
        serviceJob.cancel()
    }
}