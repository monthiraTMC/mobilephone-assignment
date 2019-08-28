package com.scb.mobilephone.model

import java.io.Serializable

data class Mobiles(
    val brand: String,
    val description: String,
    val id: Int,
    val name: String,
    val price: Double,
    val rating: Double,
    val thumbImageURL: String
) : Serializable

data class MobileDetail(
    val id: Int,
    val mobile_id: Int,
    val url: String
)