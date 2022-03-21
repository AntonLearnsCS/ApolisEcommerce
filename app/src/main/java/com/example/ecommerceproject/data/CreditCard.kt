package com.example.ecommerceproject.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CreditCard(
    @PrimaryKey
    val id : Int = 1,
    val creditCard : String)
