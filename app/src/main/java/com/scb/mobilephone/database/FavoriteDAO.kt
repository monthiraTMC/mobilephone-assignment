package com.scb.mobilephone.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteDAO{
    @Query( value = "select * from FAVORITE_LISTS")
    fun queryFavoriteLists(): List<DatabaseEntity>

    @Query( value = "select * from FAVORITE_LISTS where id = :id")
    fun queryFavoriteLists(id : Int): DatabaseEntity

    @Insert
    fun addToFavorite(favoriteEntity: DatabaseEntity)

    @Delete
    fun deleteFromFavorite(favoriteEntity: DatabaseEntity)
}
