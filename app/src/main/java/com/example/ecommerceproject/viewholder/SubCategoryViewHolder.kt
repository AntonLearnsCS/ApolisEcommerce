package com.example.ecommerceproject.viewholder

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.ecommerceproject.Constants
import com.example.ecommerceproject.R
import com.example.ecommerceproject.data.Product
import com.example.ecommerceproject.data.database.EcommerceDatabase
import com.example.ecommerceproject.databinding.SubcategoryItemViewHolderBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.runBlocking

class SubCategoryViewHolder(val binding: SubcategoryItemViewHolderBinding) :
    RecyclerView.ViewHolder(binding.root) {


private var currentQuantity = 0
    fun bind(product: Product) {
        binding.apply {
            Log.i("tag", "${product.product_image_url}")
            Picasso.get().load("${Constants.BASE_URL_IMAGES}${product.product_image_url}")
                .placeholder(R.drawable.phone_place_holder)
                .error(R.drawable.phone_place_holder)
                .into(binding.ivItemImage)

            tvItemTitle.text = product.product_name
            tvOrderItemCount.text = product.quantity.toString()
            tvOrderItemDetail.text = product.description
            tvOrderItemPrice.text = product.price
            Log.i("tag", "Rating: ${product.average_rating}")
            rbOrderItem.rating = product.average_rating.toFloat()
        }

        binding.btnOrderPlus.setOnClickListener {
            currentQuantity = binding.tvOrderItemCount.text.toString().toInt() + 1
            binding.tvOrderItemCount.text = currentQuantity.toString()
        }

        binding.btnOrderMinus.setOnClickListener {
            val quantity = binding.tvOrderItemCount.text.toString().toInt() - 1

            if (quantity > 0) {
                binding.tvOrderItemCount.text = quantity.toString()
            }
        }
        binding.btnAddToCart.setOnClickListener {
            if (currentQuantity > 0) {
                val ecommerceRepository = EcommerceDatabase.getInstance(binding.root.context)

                val sPref = EncryptedSharedPreferences.create(
                    "login_settings",
                    MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
                    binding.root.context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                )

                val currentUserId: String? = sPref.getString("currentUserId", "0")
                product.user_id = currentUserId?.toInt()
                product.quantity = currentQuantity
                //TODO: Use background thread
                    ecommerceRepository.ecommerceDao.addProduct(product)
            }
        }
    }
}