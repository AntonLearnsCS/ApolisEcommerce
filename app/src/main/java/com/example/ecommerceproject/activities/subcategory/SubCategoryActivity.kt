package com.example.ecommerceproject.activities.subcategory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.ecommerceproject.adapter.ViewPagerAdapter
import com.example.ecommerceproject.data.Category
import com.example.ecommerceproject.data.SubCategoriesResponse
import com.example.ecommerceproject.data.Subcategory
import com.example.ecommerceproject.databinding.ActivitySubCategoryBinding
import com.example.ecommerceproject.network.EcommerceApiAccessObject
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubCategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySubCategoryBinding
    private lateinit var adapter: ViewPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val selectedCategory = intent.extras?.getParcelable<Category>("category_id")


        val callRequest = selectedCategory?.category_id?.let {
            EcommerceApiAccessObject.retrofitDisplayProducts.getSubCategory(
                it
            )
        }

        callRequest?.enqueue(object : Callback<SubCategoriesResponse> {
            override fun onResponse(
                call: Call<SubCategoriesResponse>,
                response: Response<SubCategoriesResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body()?.status == 0 && response.body() != null) {
                        val listSubCategories: List<Subcategory>? = response.body()?.subcategories

                        listSubCategories?.let {
                            adapter = ViewPagerAdapter(listSubCategories)
                            binding.viewPager2Layout.adapter = adapter

                            TabLayoutMediator(
                                binding.tabLayout,
                                binding.viewPager2Layout
                            ) { tab, position ->
                                tab.text = listSubCategories[position].subcategory_name
                            }.attach()
                        }
                    }
                }
                else {
                    Log.i("tag", "request failed: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SubCategoriesResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}