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

    private fun getPictures() = viewModelScope.launch {
            _pictures.value = flickrRepository.getPictures()
    }
    fun clearDatabase()=viewModelScope.launch{
        flickrRepository.deletePictures()
    }
    fun loadAndSavePictures() = viewModelScope.launch {
            val result = flickrRepository.search()
            result.onSuccess { pictures ->
                flickrRepository.savePictures(pictures)
                getPictures()
            }.onFailure { e ->
                Log.e("ViewModel", "Network error", e)
            }
    }
}