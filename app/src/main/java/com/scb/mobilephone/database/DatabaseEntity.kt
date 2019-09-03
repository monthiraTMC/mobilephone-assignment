package com.scb.mobilephone.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.scb.mobilephone.extensions.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class DatabaseEntity(
    @PrimaryKey val id: Int,
    @NonNull var name: String,
    @NonNull val description: String,
    @NonNull val brand: String,
    val price: Double,
    val rating: Double,
    val thumbImageURL: String

)