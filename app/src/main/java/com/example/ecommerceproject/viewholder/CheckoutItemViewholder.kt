package com.example.ecommerceproject.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceproject.Constants
import com.example.ecommerceproject.R
import com.example.ecommerceproject.data.Product
import com.example.ecommerceproject.databinding.CheckoutItemViewHolderBinding
import com.squareup.picasso.Picasso

class CheckoutItemViewholder(val binding : CheckoutItemViewHolderBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(product : Product){
        val price = "$${product.price}"
        binding.tvCheckoutItemPrice.text = price
        binding.tvCheckoutItemTitle.text = product.product_name
        binding.tvCheckoutDetail.text = product.description
        Picasso.get().load("${Constants.BASE_URL_IMAGES}${product.product_image_url}")
            .placeholder(R.drawable.phone_place_holder)
            .error(R.drawable.phone_place_holder)
            .into(binding.ivCheckoutResultImage)
    }

}