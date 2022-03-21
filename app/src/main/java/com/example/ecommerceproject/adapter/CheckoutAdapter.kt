package com.example.ecommerceproject.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.ecommerceproject.activities.checkout.DeliveryFragment
import com.example.ecommerceproject.activities.checkout.PaymentFragment
import com.example.ecommerceproject.activities.checkout.SummaryFragment

class CheckoutAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> DeliveryFragment()
        1 -> PaymentFragment()
        else -> SummaryFragment()
    }
}