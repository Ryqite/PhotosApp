package com.example.pix.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [PictureDbo::class], version = 1)
@TypeConverters(Converter::class)
abstract class PictureDatabase: RoomDatabase() {
    abstract fun getPictureDao(): PictureDao
    companion object{
    fun getDB(context: Context): PictureDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            PictureDatabase::class.java,
            "PicturesDB"
        ).build()
    }
    }
}