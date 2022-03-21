package com.example.ecommerceproject.viewholder

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceproject.Constants
import com.example.ecommerceproject.R
import com.example.ecommerceproject.data.Product
import com.example.ecommerceproject.databinding.SummaryProductViewHolderBinding
import com.squareup.picasso.Picasso

class SummaryProductViewHolder (val binding : SummaryProductViewHolderBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product){
                binding.apply {
                        Picasso.get().load("${Constants.BASE_URL_IMAGES}${product.product_image_url}")
                                .placeholder(R.drawable.phone_place_holder)
                                .error(R.drawable.phone_place_holder)
                                .into(binding.ivSummaryItemImage)

                        tvSummaryItemTitle.text = product.product_name
                        tvOrderItemCount.text = product.quantity.toString()
                        tvSummaryOrderItemDetail.text = product.description
                        tvSummaryOrderItemPrice.text = product.price
                        Log.i("tag", "Rating: ${product.average_rating}")
                        //rbSummaryOrderItem.rating = product.average_rating.toFloat()
                }
        }
}