package com.example.ecommerceproject.data.database

import com.example.ecommerceproject.data.*
import com.example.ecommerceproject.network.EcommerceApiAccessObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class RemoteRepository(private val ecommerceAccessObject: EcommerceApiAccessObject) {

    fun getCategories(): Call<ProductCategoriesResponse> {
        return ecommerceAccessObject.retrofitDisplayProducts.getCategories()
    }

    fun getSubCategoryItems(subcategoryId : String) : Call<SubCategoryProductResponse>{
        return ecommerceAccessObject.retrofitDisplayProducts.getSubCategoryItems(subcategoryId)
    }

    fun getSubCategory(id: String): Call<SubCategoriesResponse> {
        return ecommerceAccessObject.retrofitDisplayProducts.getSubCategory(id)
    }

    fun getProductDetail(productId: String): Call<ProductDetailResponse> {
        return ecommerceAccessObject.retrofitDisplayProducts.getProductDetail(productId)
    }

    fun searchProduct(query: String): Call<SearchProductResponse> {
        return ecommerceAccessObject.retrofitDisplayProducts.searchProduct(query)
    }
}