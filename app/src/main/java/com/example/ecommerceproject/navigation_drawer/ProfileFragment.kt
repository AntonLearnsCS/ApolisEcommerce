package com.example.ecommerceproject.navigation_drawer

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.ecommerceproject.R
import com.example.ecommerceproject.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var sPref : SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        getUserInformation()
        initializeViews()
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun initializeViews() {
        val userName = sPref.getString("currentUserName", "anon")
        val mobileNo = sPref.getString("currentUserMobile", "")
        val email = sPref.getString("currentUserEmail", "")

        val formattedName = "Name: ${userName}"
        val formatedNo = "Mobile: ${mobileNo}"
        val formattedEmail = "Email: ${email}"
        binding.tvProfileName.text = formattedName
        binding.tvMobileProfile.text = formatedNo
        binding.tvEmailProfile.text = formattedEmail
    }

    fun getUserInformation(){
        val masterKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        sPref = EncryptedSharedPreferences.create(
            "login_settings",
            masterKey,
            binding.root.context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }


}