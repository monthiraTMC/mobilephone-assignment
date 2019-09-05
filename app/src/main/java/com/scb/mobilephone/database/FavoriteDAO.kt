package com.scb.mobilephone.database

import androidx.room.*
import com.scb.mobilephone.database.DatabaseEntity

@Dao
interface FavoriteDAO{
    @Query( value = "select * from FAVORITE_LISTS")
    fun queryFavoriteLists(): List<DatabaseEntity>

    @Query( value = "select * from FAVORITE_LISTS where id = :id")
    fun queryFavoriteLists(id : Int): DatabaseEntity

    @Insert
    fun addToFavorite(favoriteEntity: DatabaseEntity)
    @Update
    fun updateToFavorite(favoriteEntity: DatabaseEntity)
    @Delete
    fun deleteFromFavorite(favoriteEntity: DatabaseEntity)
}
