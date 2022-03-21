package com.example.ecommerceproject.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class Product(
    @Json(name = "average_rating")
    var average_rating: String = "2.5",
    @Json(name = "category_id")
    val category_id: String,
    @Json(name = "category_name")
    val category_name: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "price")
    val price: String,
    @PrimaryKey
    @Json(name = "product_id")
    val product_id: String,
    @Json(name = "product_image_url")
    val product_image_url: String,
    @Json(name = "product_name")
    val product_name: String,
    @Json(name = "sub_category_id")
    val sub_category_id: String,
    @Json(name = "subcategory_name")
    val subcategory_name: String,
    @Json(name = "quantity")
    var quantity : Int = 0,
    @Json(name = "user_id")
    var user_id : Int? = 0,
    @Json(name = "is_purchased")
    var is_purchased : Int = 0
)