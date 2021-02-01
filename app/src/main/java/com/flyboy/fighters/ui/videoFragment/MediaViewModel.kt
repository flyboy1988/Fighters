package com.flyboy.fighters.ui.videoFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.flyboy.fighters.data.repository.MediaRepository
import com.flyboy.fighters.model.MediaObject

class MediaViewModel: ViewModel() {
    private val mediaData: MutableLiveData<MutableList<MediaObject>> = MediaRepository().getMediaData()
    fun getMedia(): MutableLiveData<MutableList<MediaObject>> {
        return mediaData
    }
}