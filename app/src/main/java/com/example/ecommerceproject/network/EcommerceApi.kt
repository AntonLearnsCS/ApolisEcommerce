package com.example.ecommerceproject.network

import com.example.ecommerceproject.Constants
import com.example.ecommerceproject.data.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 * Moshi is a converter, meaning it allows you to convert Kotlin/Java objects into resource formats like JSON. Another popular converter is Gson
 */

val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


//TODO: Cache the response for categories using OKHTTP interceptor: https://blog.mindorks.com/okhttp-interceptor-making-the-most-of-it
// since the category values do not change values




interface displayProducts {
    @GET("Category")
    fun getCategories(): Call<ProductCategoriesResponse>

    @GET("SubCategory")
    fun getSubCategory(@Query("category_id") categoryId: String): Call<SubCategoriesResponse>

    @GET("SubCategory/products/{sub_category_id}")
    fun getSubCategoryItems(@Path("sub_category_id") subCategoryId: String) : Call<SubCategoryProductResponse>

}

/*
Created RoomDB and began Ecommerce API class construction

Need to refresh Retrofit and expand knowledge on POST requests. Learned that you can make POST requests using query parameters in addition to using request body
to make the POST requests. Also, I need to explore how Retrofit handles and converts JSON objects during GET and POST requests. This project can focus heavily on
On branch master

 */
interface checkoutProducts {


}


interface UserEntryInterface {
    //note: We need to add the suspend modifier for each function if we want to use the built-in
    //coroutine call adapter. Otherwise, we would not be able to take advantage of coroutines
    //and we would have to create our own call adapter. Do keep in mind that Retrofit by default
    //will execute network requests in the background thread.
    //using query parameter to pass in parameter to POST request

    //TODO: Try Posting data using request body as described here:
    //https://stackoverflow.com/questions/40817362/sending-json-in-post-request-with-retrofit2

    @Headers("Content-type:application/json")
    @POST("User/auth")
    fun login(@Body body: LoginUser?): Call<LoginResponse>

    /*
    @Body will convert your data class into a JSON object, similar to GSON's toJson()
     */
    @Headers("Content-type:application/json")
    @POST("User/register")
    fun register(@Body body: RegisterUser?): Call<RegistrationResponse>
}


object EcommerceApiAccessObject{
    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL_API)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()


    val retrofitUserEntry: UserEntryInterface by lazy {
        retrofit.create(UserEntryInterface::class.java)
    }

    val retrofitDisplayProducts : displayProducts by lazy {
        retrofit.create(displayProducts::class.java)
    }
}