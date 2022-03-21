package com.example.ecommerceproject.navigation_drawer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ecommerceproject.R
import com.example.ecommerceproject.activities.dashboard.DashBoardActivity
import com.example.ecommerceproject.activities.subcategory.SubCategoryActivity
import com.example.ecommerceproject.adapter.CategoryAdapter
import com.example.ecommerceproject.data.Category
import com.example.ecommerceproject.data.ProductCategoriesResponse
import com.example.ecommerceproject.databinding.FragmentDashBoardBinding
import com.example.ecommerceproject.network.EcommerceApiAccessObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DashBoardFragment : Fragment() {

private lateinit var adapter: CategoryAdapter
private lateinit var binding : FragmentDashBoardBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashBoardBinding.inflate(inflater, container, false)

        loadCategories()
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun loadCategories() {
        val requestCall = EcommerceApiAccessObject.retrofitDisplayProducts.getCategories()

        requestCall.enqueue(object : Callback<ProductCategoriesResponse> {
            override fun onResponse(
                call: Call<ProductCategoriesResponse>,
                response: Response<ProductCategoriesResponse>
            ) {
                binding.pbDashboardFragment.visibility = View.INVISIBLE

                if (response.isSuccessful && response.body() != null) {
                    if (response.body()?.status == 0 && response.body()?.categories != null) {
                        val listCategory: List<Category>? = response.body()?.categories

                        listCategory?.let {
                            adapter = CategoryAdapter(listCategory)
                            binding.rvDisplayCategories.layoutManager =
                                GridLayoutManager(context, DashBoardActivity.NUMBER_OF_ROWS)
                            binding.rvDisplayCategories.adapter = adapter

                            adapter.setSelectedCategoryLambda {
                                val intent =
                                    Intent(context, SubCategoryActivity::class.java)
                                intent.putExtra("category_id", it)
                                startActivity(intent)
                            }
                        }
                    }
                } else {
                    Log.i("tag", "${response.message()}")
                }
            }

            override fun onFailure(call: Call<ProductCategoriesResponse>, t: Throwable) {
                binding.pbDashboardFragment.visibility = View.INVISIBLE
                t.printStackTrace()
            }
        })
    }
}