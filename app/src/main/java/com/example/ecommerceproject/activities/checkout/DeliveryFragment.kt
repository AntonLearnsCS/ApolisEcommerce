package com.example.ecommerceproject.activities.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.ecommerceproject.data.Address
import com.example.ecommerceproject.data.CurrentAddress
import com.example.ecommerceproject.data.database.EcommerceDatabase
import com.example.ecommerceproject.databinding.DialogAddressLayoutBinding
import com.example.ecommerceproject.databinding.FragmentDeliveryBinding
import java.util.*

class DeliveryFragment : Fragment() {
    private lateinit var binding: FragmentDeliveryBinding
    private lateinit var userId: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDeliveryBinding.inflate(inflater, container, false)

        getUserInformation()

        showAddresses()

        binding.btnAddAddress.setOnClickListener {
            addressDialog()
        }
        //TODO: Make sure user selects an address
        binding.rgAddresses.setOnClickListener {
            if (binding.rgAddresses.getCheckedRadioButtonId() == -1) {
                // no radio buttons are checked
                Toast.makeText(binding.root.context, "Please select an address", Toast.LENGTH_LONG)
                    .show()
            } else {
                // get selected radio button from radioGroup

                // get selected radio button from radioGroup
                val selectedId: Int = binding.rgAddresses.getCheckedRadioButtonId()
                // find the radiobutton by returned id
                // find the radiobutton by returned id
                val selectedRadioButton =
                    binding.rgAddresses.findViewById(selectedId) as RadioButton

                //will only save one address at a time
                EcommerceDatabase.getInstance(binding.root.context).ecommerceDao.saveCheckoutAddress(
                    selectedRadioButton.tag as CurrentAddress
                )
                // one of the radio buttons is checked
            }
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    //source: https://www.geeksforgeeks.org/dynamic-radiobutton-in-kotlin/
    private fun showAddresses() {
        val listOfAddresses: List<Address> =
            EcommerceDatabase.getInstance(binding.root.context).ecommerceDao.getAddresses(userId.toInt())
        for (element in listOfAddresses) {
            val radioButton = RadioButton(binding.root.context)
            radioButton.tag = element
            val text = "${element.addressTitle}\n${element.addressText}"
            radioButton.text = text
            radioButton.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            binding.rgAddresses.addView(radioButton)
        }
    }


    private fun addressDialog() {


        val dialogBinding = DialogAddressLayoutBinding.inflate(layoutInflater)

        val builder = AlertDialog.Builder(binding.root.context)
            .setView(dialogBinding.root)
        val dialog = builder.create()
        dialog.show()

        dialogBinding.btnYesAddress.setOnClickListener {
            val addressTitle = dialogBinding.etAddressTitle.text.toString()
            val addressText = dialogBinding.etAddressText.text.toString()
            userId?.let {
                val random = Random()
                val randomPrimaryKey = random.nextInt(1000)
                val address = Address(randomPrimaryKey, it.toInt(), addressTitle, addressText)
                EcommerceDatabase.getInstance(binding.root.context).ecommerceDao.saveAddress(address)
            }
            showAddresses()
            dialog.dismiss()
        }
    }

    private fun getUserInformation() {
        val masterKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        val sPref = EncryptedSharedPreferences.create(
            "login_settings",
            masterKey,
            binding.root.context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        userId = sPref.getString("currentUserId", "0").toString()
    }
}