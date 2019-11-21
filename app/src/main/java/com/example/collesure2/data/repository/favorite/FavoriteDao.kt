package com.example.collesure2.data.repository.favorite

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite")
    fun getAll(): List<Favorite>

    @Query("SELECT * FROM favorite WHERE id = :id LIMIT 1")
    fun findById(id: Int): Favorite?

    @Query("SELECT * FROM favorite WHERE ThumbIUrl = :thumbIUrl LIMIT 1")
    fun findByThumbUrl(thumbIUrl: String): Favorite?

    @Query("SELECT * FROM favorite WHERE ImageUrl = :imageUrl LIMIT 1")
    fun findByImageUrl(imageUrl: String): Favorite?

    @Insert
    fun insert(favorite: Favorite)

    @Delete
    fun delete(favorite: Favorite)

    @Query("DELETE FROM favorite")
    fun deleteAll()
}