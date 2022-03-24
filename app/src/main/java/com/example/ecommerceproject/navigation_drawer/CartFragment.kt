package com.example.ecommerceproject.navigation_drawer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.ecommerceproject.activities.checkout.CheckoutActivity
import com.example.ecommerceproject.activities.onclicklistener.DecreasedQuantityTest
import com.example.ecommerceproject.activities.onclicklistener.IncreaseQuantityTest
import com.example.ecommerceproject.activities.onclicklistener.RemoveProductTest
import com.example.ecommerceproject.adapter.CartProductAdapter
import com.example.ecommerceproject.data.Product
import com.example.ecommerceproject.data.database.CentralRepository
import com.example.ecommerceproject.data.database.EcommerceDatabase
import com.example.ecommerceproject.data.database.LocalRepository
import com.example.ecommerceproject.data.database.RemoteRepository
import com.example.ecommerceproject.databinding.FragmentCartBinding
import com.example.ecommerceproject.network.EcommerceApiAccessObject
import java.text.FieldPosition


class CartFragment : Fragment(), DecreasedQuantityTest, IncreaseQuantityTest, RemoveProductTest {
    private lateinit var adapter : CartProductAdapter
    private lateinit var binding : FragmentCartBinding
    private lateinit var productsInCart : MutableList<Product>
    private lateinit var viewModel: NavigationSharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(layoutInflater, container,false)

        viewModel = ViewModelProvider(requireActivity()).get(NavigationSharedViewModel::class.java)

        productsInCart = mutableListOf<Product>()

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

        viewModel.cartOrders.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            var totalAmountInteger = 0

            for(element in it){
                totalAmountInteger += (element.quantity * element.price.toInt())
            }
            val totalAmountString = "$${totalAmountInteger}"
            binding.tvTotalBill.text = totalAmountString
        })

        currentUserId?.let {

            adapter = CartProductAdapter(mutableListOf(), this, this, this)
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

    override fun decreasedQuantity(price: Float) {
        val currentTotal = binding.tvTotalBill.text.toString().dropWhile { !it.isDigit() }.toFloat()
        val newTotal = "$${currentTotal - price}"
        binding.tvTotalBill.text = newTotal
    }

    override fun increaseQuantity(price: Float) {
        val currentTotal = binding.tvTotalBill.text.toString().dropWhile { !it.isDigit() }.toFloat()
        val newTotal = "$${currentTotal + price}"
        binding.tvTotalBill.text = newTotal
    }

    override fun removeProductTest(product: Product, position: Int) {


       /* productsInCart.removeAt(position)
        adapter.notifyItemChanged(position)*/

        Log.i("tag","${productsInCart}")

    }


}