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
import kotlinx.coroutines.launch

class PicturesViewModel(val flickrRepository: FlickrRepository): ViewModel() {
    private val _picturesFlow = MutableStateFlow<Result<List<Picture>>>(Result.success(emptyList()))
    val picturesFlow: StateFlow<Result<List<Picture>>> = _picturesFlow.asStateFlow()
    fun search()= viewModelScope.launch{
        _picturesFlow.value = flickrRepository.search()
    }
    fun savePictures()=viewModelScope.launch{
        val result: List<Picture> = _picturesFlow.value.fold(
            onSuccess = { it },
            onFailure = { exception ->
                Log.e("Error", "${exception.message}")
                emptyList()
            })
        flickrRepository.savePictures(result)
    }
    fun getPictures()=viewModelScope.launch{flickrRepository.getPictures()}
    fun deletePictures()=viewModelScope.launch{flickrRepository.deletePictures()}
}