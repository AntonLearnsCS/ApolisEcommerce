package com.example.ecommerceproject.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.ecommerceproject.activities.checkout.CartItemFragment
import com.example.ecommerceproject.activities.checkout.DeliveryFragment
import com.example.ecommerceproject.activities.checkout.PaymentFragment
import com.example.ecommerceproject.activities.checkout.SummaryFragment

class CheckoutAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> CartItemFragment()
        1 -> DeliveryFragment()
        2 -> PaymentFragment()
        else -> SummaryFragment()
    }
}