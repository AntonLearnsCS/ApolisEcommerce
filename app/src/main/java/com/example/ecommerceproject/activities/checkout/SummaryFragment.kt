package com.example.ecommerceproject.activities.checkout

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.ecommerceproject.R
import com.example.ecommerceproject.adapter.CartProductAdapter
import com.example.ecommerceproject.data.Product
import com.example.ecommerceproject.data.database.EcommerceDatabase
import com.example.ecommerceproject.databinding.FragmentCartBinding
import com.example.ecommerceproject.databinding.FragmentSummaryBinding

class SummaryFragment : Fragment() {

    private lateinit var adapter : CartProductAdapter
    private lateinit var binding : FragmentSummaryBinding
    private lateinit var productsInCart : MutableList<Product>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSummaryBinding.inflate(layoutInflater, container,false)

        loadCartProducts(binding.root.context)

        binding.btnCheckout.setOnClickListener {
            startActivity(Intent(binding.root.context, CheckoutActivity::class.java))
        }
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
            productsInCart =
                ecommerceDatabase.ecommerceDao.getCartProducts(it).toMutableList()
            var totalAmountInteger = 0

            for(element in productsInCart){
                totalAmountInteger += (element.quantity * element.price.toInt())
            }
            val totalAmountString = "$${totalAmountInteger}"
            binding.tvTotalBill.text = totalAmountString
            adapter = CartProductAdapter(productsInCart.toMutableList(), this, this, this)
            binding.rvCartProducts.layoutManager = LinearLayoutManager(context)
            binding.rvCartProducts.adapter = adapter
            binding.rvCartProducts.itemAnimator = null
            adapter.setOnRemoveClicked { product : Product ->
                val currentTotal = binding.tvTotalBill.text.toString().dropWhile { !it.isDigit() }.toFloat()
                val newTotal = "$${currentTotal - (product.price.toFloat() * product.quantity.toFloat())}"
                binding.tvTotalBill.text = newTotal
                product.user_id?.let {
                    EcommerceDatabase.getInstance(binding.root.context).ecommerceDao.removeProduct(it, product.product_name)
                }
            }
        }
    }
}