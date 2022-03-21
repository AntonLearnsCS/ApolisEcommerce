package com.example.ecommerceproject.navigation_drawer

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.ecommerceproject.adapter.CartProductAdapter
import com.example.ecommerceproject.data.database.EcommerceDatabase
import com.example.ecommerceproject.databinding.FragmentCartBinding


class CartFragment : Fragment() {
    private lateinit var adapter : CartProductAdapter
    private lateinit var binding : FragmentCartBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(layoutInflater, container,false)

        loadCartProducts(binding.root.context)
        // Inflate the layout for this fragment
        return binding.root
    }
    private fun loadCartProducts(context: Context) {

        val sPref = EncryptedSharedPreferences.create("login_settings", MasterKeys.getOrCreate(
            MasterKeys.AES256_GCM_SPEC),binding.root.context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)

        val currentUserId : String? = sPref.getString("currentUserId","0")

        val ecommerceDatabase = EcommerceDatabase.getInstance(context)

        currentUserId?.let {
            val productsInCart =
                ecommerceDatabase.ecommerceDao.getCartProducts(it)
            var totalAmountInteger = 0

            for(element in productsInCart){
                totalAmountInteger += (element.quantity * element.price.toInt())
            }
            var totalAmountString = "$${totalAmountInteger}"
            binding.tvTotalBill.text = totalAmountString
            adapter = CartProductAdapter(productsInCart.toMutableList())
            binding.rvCartProducts.itemAnimator = null
            binding.rvCartProducts.layoutManager = LinearLayoutManager(context)
            binding.rvCartProducts.adapter = adapter

            adapter.setQuantityDecreasedListener { price : Float ->
                val currentTotal = binding.tvTotalBill.text.toString().dropWhile { !it.isDigit() }.toFloat()
                val newTotal = currentTotal - price
                totalAmountString = "$${newTotal}"
                binding.tvTotalBill.text = totalAmountString
            }

            adapter.setQuantityIncreasedListener { price : Float ->
                val currentTotal = binding.tvTotalBill.text.toString().dropWhile { !it.isDigit() }.toFloat()
                val newTotal : Float = currentTotal + price
                totalAmountString = "$${newTotal}"

                binding.tvTotalBill.text = totalAmountString
            }

            adapter.setRemoveProductListener { price : Float ->
                val currentTotal = binding.tvTotalBill.text.toString().dropWhile { !it.isDigit() }.toFloat()
                val newTotal = currentTotal - price
                totalAmountString = "$${newTotal}"
                binding.tvTotalBill.text = totalAmountString
            }
        }
    }


}