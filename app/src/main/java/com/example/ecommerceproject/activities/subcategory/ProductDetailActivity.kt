package com.example.ecommerceproject.activities.subcategory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerceproject.Constants
import com.example.ecommerceproject.R
import com.example.ecommerceproject.adapter.CustomerReviewAdapter
import com.example.ecommerceproject.adapter.ProductDetailAdapterSpecification
import com.example.ecommerceproject.data.ProductDetailResponse
import com.example.ecommerceproject.data.ProductX
import com.example.ecommerceproject.databinding.ActivityProductDetailBinding
import com.example.ecommerceproject.network.EcommerceApiAccessObject
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var specificationAdapter : ProductDetailAdapterSpecification
    private lateinit var reviewAdapter : CustomerReviewAdapter
    private lateinit var binding : ActivityProductDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val productId : String? = intent.extras?.getString("productId")

        productId?.let {
            val callRequest = EcommerceApiAccessObject.retrofitDisplayProducts.getProductDetail(it)
            callRequest.enqueue(object : Callback<ProductDetailResponse>{
                override fun onResponse(
                    call: Call<ProductDetailResponse>,
                    response: Response<ProductDetailResponse>
                ) {
                    if (response.isSuccessful){
                        if (response.body()?.status == 0){
                            initializeViews(response.body()!!.product)
                         val specificationList = response.body()?.product?.specifications
                         val reviewList = response.body()?.product?.reviews
                            specificationList?.let {
                                specificationAdapter = ProductDetailAdapterSpecification(it)
                                binding.rvSpecifications.adapter = specificationAdapter
                                binding.rvSpecifications.layoutManager = LinearLayoutManager(this@ProductDetailActivity)
                            }
                            reviewList?.let {
                                reviewAdapter = CustomerReviewAdapter(it)
                                binding.rvReviews.adapter = reviewAdapter
                                binding.rvReviews.layoutManager = LinearLayoutManager(this@ProductDetailActivity)
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<ProductDetailResponse>, t: Throwable) {
                    t.printStackTrace()
                }

            })

        }
    }

    private fun initializeViews(productX : ProductX) {
        binding.tvProductDetailTitle.text = productX.product_name
        binding.tvProductDetailDescription.text = productX.description
        //binding.rbProductDetail.rating = productX.average_rating.toFloat()
        Log.i("tag","Url: ${productX.product_image_url}")
        Picasso.get().load("${Constants.BASE_URL_IMAGES}${productX.product_image_url}")
            .placeholder(R.drawable.phone_place_holder)
            .error(R.drawable.phone_place_holder)
            .into(binding.ivProductDetailImage)
    }
}