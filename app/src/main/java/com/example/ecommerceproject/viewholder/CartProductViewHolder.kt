package com.example.ecommerceproject.viewholder

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.ecommerceproject.Constants
import com.example.ecommerceproject.R
import com.example.ecommerceproject.activities.onclicklistener.DecreasedQuantityTest
import com.example.ecommerceproject.activities.onclicklistener.IncreaseQuantityTest
import com.example.ecommerceproject.activities.onclicklistener.RemoveProductTest
import com.example.ecommerceproject.data.Product
import com.example.ecommerceproject.data.database.EcommerceDatabase
import com.example.ecommerceproject.databinding.CartProductViewHolderBinding
import com.example.ecommerceproject.databinding.FragmentCartBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.runBlocking

class CartProductViewHolder(val binding: CartProductViewHolderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private lateinit var ecommerceDatabase: EcommerceDatabase
    private lateinit var initialQuantity: String
    private var currentQuantity = 0
    fun bind(product: Product, decreasedQuantityTest: DecreasedQuantityTest, increaseQuantityTest: IncreaseQuantityTest,
    removeProductTest: RemoveProductTest, position : Int) {

        ecommerceDatabase = EcommerceDatabase.getInstance(binding.root.context)

        initialQuantity = product.quantity.toString()

        binding.apply {
            Log.i("tag", "${product.product_image_url}")
            Picasso.get().load("${Constants.BASE_URL_IMAGES}${product.product_image_url}")
                .placeholder(R.drawable.phone_place_holder)
                .error(R.drawable.phone_place_holder)
                .into(binding.ivCartItemImage)

            tvCartItemTitle.text = product.product_name
            tvCartItemCount.text = product.quantity.toString()
            tvCartItemDetail.text = product.description
            tvCartItemPrice.text = product.price
            Log.i("tag", "Rating: ${product.average_rating}")
            rbCartOrderItem.rating = product.average_rating.toFloat()
        }

        binding.btnCartOrderPlus.setOnClickListener {
            currentQuantity = binding.tvCartItemCount.text.toString().toInt() + 1
            updateQuantity(product)
            increaseQuantityTest.increaseQuantity(product.price.toFloat())
        }

        binding.btnCartOrderMinus.setOnClickListener {
            currentQuantity = binding.tvCartItemCount.text.toString().toInt() - 1

            if (currentQuantity > 0) {
                decreasedQuantityTest.decreasedQuantity(product.price.toFloat())
                updateQuantity(product)
            }
        }

        /*binding.btnRemoveFromCart.setOnClickListener {

            val sPref = EncryptedSharedPreferences.create(
                "login_settings", MasterKeys.getOrCreate(
                    MasterKeys.AES256_GCM_SPEC
                ), binding.root.context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )

            val currentUserId: String? = sPref.getString("currentUserId", "0")

            removeProductTest.removeProductTest(product, position)
            product.user_id = currentUserId?.toInt()
            //TODO: Use background thread
            currentUserId?.let {
                ecommerceDatabase.ecommerceDao.removeProduct(
                    currentUserId.toInt(),
                    product.product_name
                )
            }
        }*/
    }

    fun updateQuantity(product: Product) {
        product.quantity = currentQuantity
        binding.tvCartItemCount.text = currentQuantity.toString()
        ecommerceDatabase.ecommerceDao.addProduct(product)
    }
}