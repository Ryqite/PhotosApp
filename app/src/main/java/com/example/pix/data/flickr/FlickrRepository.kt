package com.example.pix.data.flickr

import android.util.Log
import com.example.pix.data.flickr.mapper.toDbo
import com.example.pix.data.flickr.mapper.toEntity
import com.example.pix.data.flickr.mapper.toPicture
import com.example.pix.data.room.PictureDatabase
import com.example.pix.data.room.PictureDbo
import com.example.pix.domain.entity.Picture

class FlickrRepository(
    private val database: PictureDatabase,
    private val defaultQuality: String = "w"
) {
    suspend fun search(text: String = "cats", page: Int = 1, count: Int = 100): Result<List<Picture>> =
        runCatching {
        val result = FlickrRetrofit.api.search(text, page, count)
        result.photos?.photo?.map { it.toEntity(defaultQuality) }?: emptyList() }

    suspend fun savePictures(pictures: List<Picture>) {
        val pictureDbo = pictures.map { it.toDbo() }
        database.getPictureDao().insertAll(pictureDbo)
    }
    suspend fun getPictures(): List<Picture>{
        Log.d("Repository", "Getting pictures from DB")
        return database.getPictureDao().getAll().map { it.toPicture() }
    }
    suspend fun deletePictures()=database.getPictureDao().clearAll()
}