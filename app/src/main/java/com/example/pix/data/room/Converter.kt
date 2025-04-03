package com.example.pix.data.room

import androidx.room.TypeConverter
import com.example.pix.domain.entity.Picture

class Converter {
    @TypeConverter
    fun fromPicture(picture: Picture): String{
        return picture.url
    }
    @TypeConverter
    fun toPicture(url: String): Picture{
        return Picture(url,url)
    }
}