package com.example.imagesearch.data.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class SearchDto(
    @PrimaryKey
    @ColumnInfo(name = "search_query")
    @SerializedName("query")
    val query: String,
    @ColumnInfo(name = "num_pages")
    @SerializedName("num_pages")
    val numPages: Int,
    @SerializedName("images")
    val images: List<ImageDto>
)