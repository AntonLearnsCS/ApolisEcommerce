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
import com.example.ecommerceproject.data.*
import com.example.ecommerceproject.data.database.EcommerceDatabase
import com.example.ecommerceproject.databinding.DialogAddressLayoutBinding
import com.example.ecommerceproject.databinding.FragmentDeliveryBinding
import com.example.ecommerceproject.network.EcommerceApiAccessObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
        val callRequest: Call<ListUserAddressResponse> =
            EcommerceApiAccessObject.retrofitCheckoutUser.getAllUserAddresses(userId)

        callRequest.enqueue(object : Callback<ListUserAddressResponse> {
            override fun onResponse(
                call: Call<ListUserAddressResponse>,
                response: Response<ListUserAddressResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body()?.status == 0) {
                        val listResponseAddresse = response.body()?.addresses
                        listResponseAddresse?.let {
                            for (element in it) {
                                val radioButton = RadioButton(binding.root.context)

                                val text = "${element.title}\n${element.address}"
                                radioButton.text = text

                                radioButton.setOnClickListener {
                                    Log.i("tagDelivery", "payment clicked ${element}")

                                    EcommerceDatabase.getInstance(binding.root.context).ecommerceDao.saveCheckoutAddress(
                                        CurrentAddress(
                                            1,
                                            userId.toInt(),
                                            element.title,
                                            element.address
                                        )
                                    )
                                }
                                radioButton.layoutParams = LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT
                                )
                                binding.rgAddresses.addView(radioButton)
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ListUserAddressResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })

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
            userId.let {
                saveAddress(UserAddress(userId.toInt(), addressTitle, addressText))
            }
            showAddresses()
            dialog.dismiss()
        }
        dialogBinding.btnCancelAddress.setOnClickListener {
            dialog.dismiss()
        }
    }

    fun saveAddress(userAddress: UserAddress) {
        val callRequest = EcommerceApiAccessObject.retrofitCheckoutUser.saveUserAddress(userAddress)

        callRequest.enqueue(object : Callback<GenericPostResponse> {
            override fun onResponse(
                call: Call<GenericPostResponse>,
                response: Response<GenericPostResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body()?.status == 0) {
                        Toast.makeText(
                            binding.root.context,
                            "Address saved succesfully",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        binding.root.context,
                        "Address failed to save. Please Retry.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<GenericPostResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })

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