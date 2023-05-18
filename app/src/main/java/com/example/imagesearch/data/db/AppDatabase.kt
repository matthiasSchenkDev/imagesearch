package com.example.imagesearch.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.imagesearch.data.dto.ImageDto
import com.example.imagesearch.data.dto.SearchDto

@Database(entities = [ImageDto::class, SearchDto::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun imageDao(): ImageDao
    abstract fun searchDao(): SearchDao
}