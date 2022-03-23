package com.example.ecommerceproject.activities.checkout

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.ecommerceproject.adapter.CheckoutItemAdapter
import com.example.ecommerceproject.data.Product
import com.example.ecommerceproject.data.database.EcommerceDatabase
import com.example.ecommerceproject.databinding.FragmentCartItemBinding

class CartItemFragment : Fragment() {

    private lateinit var binding : FragmentCartItemBinding
    private lateinit var adapter : CheckoutItemAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartItemBinding.inflate(inflater, container, false)

        loadOrders(binding.root.context)
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun loadOrders(context: Context) {

        val sPref = EncryptedSharedPreferences.create("login_settings", MasterKeys.getOrCreate(
            MasterKeys.AES256_GCM_SPEC),binding.root.context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)

        val currentUserId : String? = sPref.getString("currentUserId","0")

        val ecommerceDatabase = EcommerceDatabase.getInstance(context)

        currentUserId?.let {
            val cartProducts = mutableListOf<Product>()
            cartProducts.addAll(ecommerceDatabase.ecommerceDao.getCartProducts(it))

            adapter = CheckoutItemAdapter(cartProducts)
            binding.rvListItemCheckout.itemAnimator = null
            binding.rvListItemCheckout.layoutManager = LinearLayoutManager(context)
            binding.rvListItemCheckout.adapter = adapter
        }
    }
}