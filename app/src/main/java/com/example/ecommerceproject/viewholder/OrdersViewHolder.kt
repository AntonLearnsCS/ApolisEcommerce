package com.example.ecommerceproject.viewholder

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.ecommerceproject.Constants
import com.example.ecommerceproject.R
import com.example.ecommerceproject.data.Product
import com.example.ecommerceproject.data.database.EcommerceDatabase
import com.example.ecommerceproject.databinding.OrdersViewHolderBinding
import com.squareup.picasso.Picasso
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

class OrdersViewHolder(val binding : OrdersViewHolderBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(product: Product) {
        binding.apply {
            Log.i("tag", "${product.product_image_url}")
            Picasso.get().load("${Constants.BASE_URL_IMAGES}${product.product_image_url}")
                .placeholder(R.drawable.phone_place_holder)
                .error(R.drawable.phone_place_holder)
                .into(binding.ivOrderItemImage)

            tvOrderItemCount.text = product.quantity.toString()
            tvOrderItemTitle.text = product.product_name
            tvOrderItemDetail.text = product.description
            tvOrderItemPrice.text = product.price
            Log.i("tag", "Rating: ${product.average_rating}")
            //rbPastOrderItem.rating = product.average_rating.toFloat()
        }
    }
}