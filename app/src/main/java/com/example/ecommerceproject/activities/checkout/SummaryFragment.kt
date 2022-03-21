package com.example.ecommerceproject.activities.checkout

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.ecommerceproject.R
import com.example.ecommerceproject.activities.dashboard.DashBoardActivity
import com.example.ecommerceproject.adapter.SummaryProductAdapter
import com.example.ecommerceproject.data.CurrentAddress
import com.example.ecommerceproject.data.Product
import com.example.ecommerceproject.data.database.EcommerceDatabase
import com.example.ecommerceproject.databinding.FragmentSummaryBinding

class SummaryFragment : Fragment() {

    private lateinit var adapter : SummaryProductAdapter
    private lateinit var binding : FragmentSummaryBinding
    private lateinit var productsInSummary : MutableList<Product>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSummaryBinding.inflate(layoutInflater, container,false)

        loadSummaryProducts(binding.root.context)

        initializeViews()

        binding.btnConfirmSummary.setOnClickListener {
            checkProvidedInformation()
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun initializeViews() {
        val database = EcommerceDatabase.getInstance(binding.root.context)
        var selectedPaymentMethod : String? = null
        database.ecommerceDao.getSelectedCreditCard()?.let {selectedPaymentMethod = it.creditCard }

        val selectedAddress : CurrentAddress? = database.ecommerceDao.getSelectedAddress()

        if (selectedPaymentMethod == null){
            binding.tvPaymentMethodSelectedSummary.text = "Please select method"
            binding.tvPaymentMethodSelectedSummary.tag = false
        }
        else{
            binding.tvPaymentMethodSelectedSummary.tag = true
            binding.tvPaymentMethodSelectedSummary.text = selectedPaymentMethod
        }
        
        if (selectedAddress == null){
            binding.tvAddressTitleSummary.text = "Please select Address"
            binding.tvAddressTitleSummary.tag = false
        }
        else{
            binding.tvAddressTitleSummary.tag = true
            binding.tvAddressTitleSummary.text = selectedAddress.addressTitle
            binding.tvAddressTextSummary.text = selectedAddress.addressText
        }


    }

    private fun checkProvidedInformation() {
        if (binding.tvPaymentMethodSelectedSummary.tag == false || binding.tvAddressTitleSummary.tag == false){
                val builder = AlertDialog.Builder(binding.root.context)
                    .setTitle("Missing information")
                    .setMessage("Please see missing information")
                    .setPositiveButton("Ok"){
                        dialog, position ->
                        dialog.dismiss()
                    }
                val dialog = builder.create()
                dialog.show()
        }
        else{
            val database = EcommerceDatabase.getInstance(binding.root.context)
            val builder = AlertDialog.Builder(binding.root.context)
                .setTitle("Products on the way:")
                .setMessage("Thank you for shopping!")
                .setPositiveButton("Ok"){
                        dialog, position ->
                    for (element in productsInSummary){
                        //marks product as purchased since using same dataclass for orders and cart
                        element.is_purchased = 1
                        database.ecommerceDao.saveOrderedProduct(element)
                    }
                    dialog.dismiss()
                    startActivity(Intent(binding.root.context, DashBoardActivity::class.java))
                }
            val dialog = builder.create()
            dialog.show()
        }
    }

    private fun loadSummaryProducts(context: Context) {

        val sPref = EncryptedSharedPreferences.create("login_settings", MasterKeys.getOrCreate(
            MasterKeys.AES256_GCM_SPEC),binding.root.context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)

        val currentUserId : String? = sPref.getString("currentUserId","0")

        val ecommerceDatabase = EcommerceDatabase.getInstance(context)

        currentUserId?.let {
            productsInSummary =
                ecommerceDatabase.ecommerceDao.getCartProducts(it).toMutableList()
            var totalAmountInteger = 0

            for(element in productsInSummary){
                totalAmountInteger += (element.quantity * element.price.toInt())
            }
            val totalAmountString = "$${totalAmountInteger}"
            binding.tvTotalBillSummaryNumber.text = totalAmountString
            adapter = SummaryProductAdapter(productsInSummary.toMutableList())
            binding.rvSummaryList.layoutManager = LinearLayoutManager(context)
            binding.rvSummaryList.adapter = adapter
            binding.rvSummaryList.itemAnimator = null
        }
    }
}