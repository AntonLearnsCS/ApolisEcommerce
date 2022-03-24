package com.example.ecommerceproject.data.database

import com.example.ecommerceproject.data.*
import com.example.ecommerceproject.network.EcommerceApiAccessObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class RemoteRepository (val ecommerceAccessObject: EcommerceApiAccessObject) {

    fun getCategories(): Call<ProductCategoriesResponse>{
        return ecommerceAccessObject.retrofitDisplayProducts.getCategories()
    }


    fun getSubCategory(): Call<SubCategoriesResponse>{

    }

    @GET("SubCategory/products/{sub_category_id}")
    fun getSubCategoryItems(@Path("sub_category_id") subCategoryId: String) : Call<SubCategoryProductResponse>

    @GET("Product/details/{product_id}")
    fun getProductDetail(@Path("product_id") productId : String) : Call<ProductDetailResponse>

    @GET("Product/search")
    fun searchProduct(@Query("query") searchText : String) : Call<SearchProductResponse>

}