package com.example.imagesearch.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.imagesearch.data.dto.SearchDto

@Dao
interface SearchDao {

    @Query("SELECT * FROM searchdto WHERE search_query=:query")
    fun getSearch(query: String): SearchDto?

    @Insert
    fun insertSearch(search: SearchDto)

}