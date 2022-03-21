package com.example.ecommerceproject.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CreditCard(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val creditCard : String)
