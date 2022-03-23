package com.example.ecommerceproject.activities.search_product

import android.app.SearchManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.ecommerceproject.Constants
import com.example.ecommerceproject.R
import com.example.ecommerceproject.activities.dashboard.DashBoardActivity
import com.example.ecommerceproject.data.Product
import com.example.ecommerceproject.data.ProductDetailResponse
import com.example.ecommerceproject.data.ProductXX
import com.example.ecommerceproject.data.SearchProductResponse
import com.example.ecommerceproject.data.database.EcommerceDatabase
import com.example.ecommerceproject.databinding.ActivitySearchProductBinding
import com.example.ecommerceproject.network.EcommerceApiAccessObject
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchProductActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySearchProductBinding
    private lateinit var currentProduct : ProductXX
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val query : String? = intent.getStringExtra("query")

        searchQuery(query)

        initializeEvents()
    }

    private fun initializeEvents() {
        binding.btnSearchResultAdd.setOnClickListener {
            /*
             val average_rating: String,
    val category_id: String,
    val category_name: String,
    val description: String,
    val price: String,
    val product_id: String,
    val product_image_url: String,
    val product_name: String,
    val sub_category_id: String,
    val subcategory_name: String
             */
            EcommerceDatabase.getInstance(this).ecommerceDao.addProduct(Product(
                average_rating = currentProduct.average_rating,
                category_id = currentProduct.category_id,
                category_name = currentProduct.category_name,
                description = currentProduct.description,
                price = currentProduct.price,
                product_id = currentProduct.product_id,
                product_image_url = currentProduct.product_image_url,
                product_name = currentProduct.product_name,
                sub_category_id = currentProduct.sub_category_id,
                subcategory_name = currentProduct.subcategory_name)
            )
        }
    }

    fun searchQuery(query : String?){
        Log.i("tag","onNewIntent called ${query}")
        //use the query to search
        query?.let {
            val callRequest = EcommerceApiAccessObject.retrofitDisplayProducts.searchProduct(it)
            callRequest.enqueue(object : Callback<SearchProductResponse> {
                override fun onResponse(
                    call: Call<SearchProductResponse>,
                    response: Response<SearchProductResponse>
                ) {
                    if(response.isSuccessful){
                        if (response.body()?.status == 0){
                            response.body()?.products?.let {
                                //assuming only one product is returned
                                currentProduct = it[0]
                            }
                            displaySearchResult()
                        }
                    }
                    else{
                        Toast.makeText(this@SearchProductActivity,"${query} not found", Toast.LENGTH_LONG).show()
                        binding.tvSearchProductNotFound.visibility = View.VISIBLE
                    }
                }

                override fun onFailure(call: Call<SearchProductResponse>, t: Throwable) {
                    t.printStackTrace()
                }

            })
        }
    }


    private fun displaySearchResult() {
        binding.apply {
            Picasso.get().load("${Constants.BASE_URL_IMAGES}${currentProduct.product_image_url}")
                .placeholder(R.drawable.phone_place_holder)
                .error(R.drawable.phone_place_holder)
                .into(binding.ivSearchResultImage)
            tvSearchResultTitle.text = currentProduct.product_name
            tvSearchResultDetail.text = currentProduct.description
            val textPrice = "$${currentProduct.price}"
            tvSearchResultPrice.text = textPrice
        }
    }
}