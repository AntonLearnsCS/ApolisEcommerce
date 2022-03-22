package com.example.ecommerceproject.activities.checkout

import android.os.Bundle
import android.util.Log
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
import com.example.ecommerceproject.data.UserAddress
import com.example.ecommerceproject.data.database.EcommerceDatabase
import com.example.ecommerceproject.databinding.DialogAddressLayoutBinding
import com.example.ecommerceproject.databinding.FragmentDeliveryBinding
import com.example.ecommerceproject.network.EcommerceApiAccessObject
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
        return binding.root
    }

    //source: https://www.geeksforgeeks.org/dynamic-radiobutton-in-kotlin/
    private fun showAddresses() {
        binding.rgAddresses.removeAllViews()
        val listOfAddresses: List<Address> =
            EcommerceDatabase.getInstance(binding.root.context).ecommerceDao.getAddresses(userId.toInt())
        for (element in listOfAddresses) {
            val radioButton = RadioButton(binding.root.context)
            radioButton.tag = CurrentAddress(addressPrimaryKey = element.addressPrimaryKey, user_id = element.user_id, addressTitle = element.addressTitle,
            addressText = element.addressText)
            val text = "${element.addressTitle}\n${element.addressText}"
            radioButton.text = text

            radioButton.setOnClickListener {
                Log.i("tagDelivery","payment clicked ${element}")

                EcommerceDatabase.getInstance(binding.root.context).ecommerceDao.saveCheckoutAddress(
                    CurrentAddress(1, userId.toInt(),element.addressTitle,element.addressText)
                )
            }
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
                saveAddress(UserAddress(userId.toInt(), addressTitle, addressText))

                val address = Address(randomPrimaryKey, it.toInt(), addressTitle, addressText)
                EcommerceDatabase.getInstance(binding.root.context).ecommerceDao.saveAddress(address)
            }
            showAddresses()
            dialog.dismiss()
        }
    }

    fun saveAddress(userAddress: UserAddress){
        EcommerceApiAccessObject.retrofitCheckoutUser.saveUserAddress()
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