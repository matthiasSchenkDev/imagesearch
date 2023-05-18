package com.example.imagesearch.data.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["search_query", "num_page"])
data class SearchDto(
    @ColumnInfo(name = "search_query")
    @SerializedName("query")
    val query: String,
    @ColumnInfo(name = "num_page")
    @SerializedName("num_page")
    val numPage: Int,
    @SerializedName("images")
    val images: List<ImageDto>
)