package com.example.ecommerceproject.data

data class ListUserAddressResponse(
    val addresses: List<Addresse>,
    val message: String,
    val status: Int
)