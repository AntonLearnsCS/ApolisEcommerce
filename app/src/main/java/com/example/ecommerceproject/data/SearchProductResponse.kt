package com.example.ecommerceproject.data

data class SearchProductResponse(
    val message: String,
    val products: List<ProductXX>,
    val status: Int
)