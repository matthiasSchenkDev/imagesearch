package com.example.imagesearch.data.db

import androidx.room.TypeConverter
import com.example.imagesearch.data.dto.ImageDto
import com.example.imagesearch.data.dto.SearchDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromSearchDtoString(value: SearchDto): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toSearchDto(value: String): SearchDto {
        return gson.fromJson(value, object : TypeToken<SearchDto>() {}.type)
    }

    @TypeConverter
    fun fromImageDtoString(value: List<ImageDto>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toImageDto(value: String): List<ImageDto> {
        return gson.fromJson(value, object : TypeToken<List<ImageDto>>() {}.type)
    }

}