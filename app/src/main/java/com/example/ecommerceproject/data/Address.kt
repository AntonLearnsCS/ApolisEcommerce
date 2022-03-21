package com.example.ecommerceproject.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Address(
    @PrimaryKey
    val addressPrimaryKey : Int,
    val user_id : Int,
    val addressTitle : String,
    val addressText : String)

@Entity
data class CurrentAddress(
    @PrimaryKey
    val addressPrimaryKey : Int,
    val user_id : Int,
    val addressTitle : String,
    val addressText : String)
