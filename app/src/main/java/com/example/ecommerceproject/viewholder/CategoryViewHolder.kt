package com.example.ecommerceproject.viewholder

import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceproject.Constants
import com.example.ecommerceproject.data.Category
import com.example.ecommerceproject.databinding.CategoryViewHolderBinding
import com.squareup.picasso.Picasso

class CategoryViewHolder (val binding : CategoryViewHolderBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(category: Category){
        binding.tvCategoryTitle.text = category.category_name
        Picasso.get().load("${Constants.BASE_URL_IMAGES}${category.category_image_url}").into(binding.ivCategoryImage);
    }
}