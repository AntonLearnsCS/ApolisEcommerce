package com.example.ecommerceproject.activities.checkout

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.Toast
import com.example.ecommerceproject.data.CreditCard
import com.example.ecommerceproject.data.CurrentAddress
import com.example.ecommerceproject.data.database.EcommerceDatabase
import com.example.ecommerceproject.databinding.FragmentPaymentBinding


class PaymentFragment : Fragment() {

   private lateinit var binding : FragmentPaymentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentBinding.inflate(inflater, container,false)

            showPaymentMethods()
        // Inflate the layout for this fragment
        return binding.root
    }
    var counter = 1
    private fun showPaymentMethods() {
        val listOfPaymentMethods = listOf<String>("VISA","MASTERCARD","BTC","ETH","PAYPAL","DEUTSCHE")
        for (element in listOfPaymentMethods) {
            val radioButton = RadioButton(binding.root.context)
            radioButton.text = element
            radioButton.id = counter
            counter++
            radioButton.setOnClickListener {
                Log.i("tagPayment","payment clicked ${element}")
                EcommerceDatabase.getInstance(binding.root.context).ecommerceDao.saveCreditCard(
                    CreditCard(1,element)
                )
            }
            radioButton.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            binding.rgPayment.addView(radioButton)
        }
    }
}