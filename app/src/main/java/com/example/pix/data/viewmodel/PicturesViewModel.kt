package com.example.pix.data.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pix.data.flickr.FlickrRepository
import com.example.pix.data.room.PictureDbo
import com.example.pix.domain.entity.Picture
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class PicturesViewModel(private val flickrRepository: FlickrRepository) : ViewModel() {
    private val _pictures = MutableStateFlow<List<Picture>>(emptyList())
    val pictures: StateFlow<List<Picture>> = _pictures.asStateFlow()

    init {
        Log.d("ViewModel", "ViewModel initialized")
        loadPicturesFromDb()
    }

    private fun loadPicturesFromDb() = viewModelScope.launch {
        try {
            Log.d("ViewModel", "Starting DB load")
            _pictures.value = flickrRepository.getPictures()
            Log.d("ViewModel", "DB returned ${pictures.value.size} pictures")
        } catch (e: Exception) {
            Log.e("ViewModel", "DB load error", e)
        }
    }

    fun loadAndSavePictures() = viewModelScope.launch {
        try {
            Log.d("ViewModel", "Starting network load")
            val result = flickrRepository.search()
            result.onSuccess { pictures ->
                Log.d("ViewModel", "Network returned ${pictures.size} pictures")
                flickrRepository.savePictures(pictures)
            }.onFailure { e ->
                Log.e("ViewModel", "Network error", e)
            }
        } catch (e: Exception) {
            Log.e("ViewModel", "Load error", e)
        }
    }
}