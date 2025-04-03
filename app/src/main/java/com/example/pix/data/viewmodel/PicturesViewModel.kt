package com.example.pix.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pix.data.flickr.FlickrRepository
import com.example.pix.data.room.PictureDbo
import kotlinx.coroutines.launch

class PicturesViewModel(val flickrRepository: FlickrRepository): ViewModel() {
    fun search()= viewModelScope.launch{
        val response = flickrRepository.search()
    }
    fun savePictures(pictureDbo: List<PictureDbo>)=viewModelScope.launch{
        flickrRepository.savePictures(pictureDbo)
    }
    fun getPictures()=viewModelScope.launch{flickrRepository.getPictures()}
    fun deletePictures()=viewModelScope.launch{flickrRepository.deletePictures()}
}