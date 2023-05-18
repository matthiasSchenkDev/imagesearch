package com.example.imagesearch.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.imagesearch.data.dto.SearchDto

@Dao
interface SearchDao {

    @Query("SELECT * FROM searchdto WHERE search_query = :query AND num_page = :page")
    fun getSearch(query: String, page: Int): SearchDto?

    @Insert
    fun insertSearch(search: SearchDto)

}