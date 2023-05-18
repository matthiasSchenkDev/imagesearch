package com.example.imagesearch.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.imagesearch.data.dto.ImageDto

@Dao
interface ImageDao {

    @Query("SELECT * FROM imagedto WHERE id=:id")
    fun getImage(id: Int): ImageDto?

    @Insert
    fun insertImage(image: ImageDto)

}