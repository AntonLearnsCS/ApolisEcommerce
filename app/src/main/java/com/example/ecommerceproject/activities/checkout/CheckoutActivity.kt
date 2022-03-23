package com.example.ecommerceproject.activities.checkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ecommerceproject.adapter.CheckoutAdapter
import com.example.ecommerceproject.databinding.ActivityCheckoutBinding
import com.google.android.material.tabs.TabLayoutMediator

class CheckoutActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCheckoutBinding
    private lateinit var adapter : CheckoutAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = CheckoutAdapter(this)
        binding.vpCheckout.adapter = CheckoutAdapter(this)

        val listCheckoutTitles = listOf("Cart","Delivery","Payment","Summary")
        TabLayoutMediator(binding.tabLayoutCheckout, binding.vpCheckout) { tab, position ->
            tab.text = listCheckoutTitles[position]
        }.attach()

    }
}