package com.example.ecommerceproject.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class User(
    @Json(name = "email_id")
    val email_id: String,
    @Json(name = "full_name")
    val full_name: String,
    @Json(name = "mobile_no")
    val mobile_no: String,
    @PrimaryKey
    @Json(name = "user_id")
    val user_id: String
)