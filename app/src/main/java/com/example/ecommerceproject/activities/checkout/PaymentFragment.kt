package com.example.ecommerceproject.activities.checkout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import com.example.ecommerceproject.R
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


        //TODO: Make sure user selects an address
        binding.rgPayment.setOnClickListener {
            if (binding.rgPayment.getCheckedRadioButtonId() == -1) {
                // no radio buttons are checked
                Toast.makeText(binding.root.context, "Please select an address", Toast.LENGTH_LONG)
                    .show()
            } else {
                // get selected radio button from radioGroup

                // get selected radio button from radioGroup
                val selectedId: Int = binding.rgPayment.getCheckedRadioButtonId()
                // find the radiobutton by returned id
                // find the radiobutton by returned id
                val selectedRadioButton =
                    binding.rgPayment.findViewById(selectedId) as RadioButton

                //will only save one address at a time
                EcommerceDatabase.getInstance(binding.root.context).ecommerceDao.saveCreditCard(
                    CreditCard(0,selectedRadioButton.text.toString())
                )
                // one of the radio buttons is checked
            }
        }

        // Inflate the layout for this fragment
        return binding.root
    }

}