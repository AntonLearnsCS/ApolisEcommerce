package com.example.ecommerceproject.viewholder

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceproject.adapter.SubCategoryItemAdapter
import com.example.ecommerceproject.data.Product
import com.example.ecommerceproject.data.SubCategoryProductResponse
import com.example.ecommerceproject.data.Subcategory
import com.example.ecommerceproject.databinding.ViewPagerViewHolderBinding
import com.example.ecommerceproject.network.EcommerceApiAccessObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewPagerHolder(val binding: ViewPagerViewHolderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private lateinit var adapter: SubCategoryItemAdapter
    fun bind(subcategoryId: String) {
        val callRequest =
            EcommerceApiAccessObject.retrofitDisplayProducts.getSubCategoryItems("${subcategoryId}")
        callRequest.enqueue(object : Callback<SubCategoryProductResponse> {
            override fun onResponse(
                call: Call<SubCategoryProductResponse>,
                response: Response<SubCategoryProductResponse>
            ) {
                binding.pbViewPager.visibility = View.INVISIBLE

                if (response.isSuccessful) {
                    if (response.body()?.status == 0 && response.body()?.products != null) {
                        val listSubCategoryProducts: MutableList<Product>? = response.body()?.products as MutableList<Product>?

                        listSubCategoryProducts?.let {
                            adapter = SubCategoryItemAdapter(it)
                            binding.rvDisplaySubcategoryProducts.adapter = adapter
                            binding.rvDisplaySubcategoryProducts.layoutManager =
                                LinearLayoutManager(binding.root.context)
                        }
                    }
                    else{
                        Log.i("tagViewPagerHolder", "failed")
                    }
                } else {
                    Log.i("tagViewPager", "response not succesful")
                }
            }

            override fun onFailure(call: Call<SubCategoryProductResponse>, t: Throwable) {
                binding.pbViewPager.visibility = View.INVISIBLE
                t.printStackTrace()
            }

        })
    }
}