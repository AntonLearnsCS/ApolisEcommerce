package com.example.ecommerceproject.data

data class ProductCategoriesResponse(
    val categories: List<Category>,
    val message: String,
    val status: Int
)