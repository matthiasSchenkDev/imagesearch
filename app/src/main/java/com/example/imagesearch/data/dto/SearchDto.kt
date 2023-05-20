package com.example.imagesearch.data.dto

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["search_query", "num_page"])
data class SearchDto(
    @ColumnInfo(name = "search_query") val query: String,
    @ColumnInfo(name = "num_page") val numPage: Int,
    val images: List<ImageDto>
)