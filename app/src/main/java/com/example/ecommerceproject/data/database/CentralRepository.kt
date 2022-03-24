package com.example.ecommerceproject.data.database

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ecommerceproject.data.Product
import com.example.ecommerceproject.data.SubCategoriesResponse
import com.example.ecommerceproject.data.SubCategoryProductResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CentralRepository(
    val localRepository: LocalRepository,
    val remoteRepository: RemoteRepository
) {

    private val productsList = MutableLiveData<List<Product>>()

    private val subcategoryProductList = MutableLiveData<List<Product>>()

    fun getCartOrders( userId : String) : LiveData<List<Product>>{
        return localRepository.getCartProducts(userId)
    }

    fun getPastOrders( userId : String) : LiveData<List<Product>>{
        return localRepository.getOrderedProducts(userId)
    }


    //returns list of products in subcategory
    fun getProducts(subcategoryId: String): MutableLiveData<List<Product>> {
        val callRequest = remoteRepository.getSubCategoryItems(subcategoryId)

        callRequest.enqueue(object : Callback<SubCategoryProductResponse> {
            override fun onResponse(
                call: Call<SubCategoryProductResponse>,
                response: Response<SubCategoryProductResponse>
            ) {
                if (response.isSuccessful) {
                    val listSubcategories = response.body()?.products

                    listSubcategories?.let {
                        subcategoryProductList.postValue(it)
                    }
                }
                else {
                    Log.i("tagError", "Error: ${response.body()?.message}")
                }
            }

            override fun onFailure(call: Call<SubCategoryProductResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })
        return subcategoryProductList
    }
}