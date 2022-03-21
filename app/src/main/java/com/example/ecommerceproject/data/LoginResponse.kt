package com.example.ecommerceproject.data

data class LoginResponse(
    val message: String,
    val status: Int,
    val user: User
)