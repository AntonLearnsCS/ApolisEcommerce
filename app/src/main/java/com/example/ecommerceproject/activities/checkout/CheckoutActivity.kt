package com.example.ecommerceproject.activities.checkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.ecommerceproject.adapter.CheckoutAdapter
import com.example.ecommerceproject.data.User
import com.example.ecommerceproject.data.database.CentralRepository
import com.example.ecommerceproject.data.database.EcommerceDatabase
import com.example.ecommerceproject.data.database.LocalRepository
import com.example.ecommerceproject.data.database.RemoteRepository
import com.example.ecommerceproject.databinding.ActivityCheckoutBinding
import com.example.ecommerceproject.navigation_drawer.NavigationSharedViewModelFactory
import com.example.ecommerceproject.network.EcommerceApiAccessObject
import com.google.android.material.tabs.TabLayoutMediator

class CheckoutActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCheckoutBinding
    private lateinit var adapter : CheckoutAdapter
    private lateinit var viewModel : CheckoutSharedViewModel
    private lateinit var currentUser : User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = CheckoutAdapter(this)
        binding.vpCheckout.adapter = CheckoutAdapter(this)
        getCurrentUser()
        initializeViewModel()
        val listCheckoutTitles = listOf("Cart","Delivery","Payment","Summary")
        TabLayoutMediator(binding.tabLayoutCheckout, binding.vpCheckout) { tab, position ->
            tab.text = listCheckoutTitles[position]
        }.attach()
    }

    private fun initializeViewModel() {
        val localRepository = LocalRepository(EcommerceDatabase.getInstance(binding.root.context))
        val remoteRepository = RemoteRepository(EcommerceApiAccessObject)
        val centralRepository = CentralRepository(localRepository,remoteRepository)
        val checkoutSharedViewModelFactory = CheckoutSharedViewModelFactory(currentUser.user_id, centralRepository)
        viewModel = ViewModelProvider(this, checkoutSharedViewModelFactory).get(CheckoutSharedViewModel::class.java)
    }

    private fun getCurrentUser() {
        val masterKeys = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        val sPref = EncryptedSharedPreferences.create(
            "login_settings",
            masterKeys,
            this,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        val currentUserId = sPref.getString("currentUserId", "")
        val currentUserEmail = sPref.getString("currentUserEmail","")
        val currentUserMobile = sPref.getString("currentUserMobile","")
        val currentUserName = sPref.getString("currentUserName","")
        if (currentUserEmail != null && currentUserId != null && currentUserName != null && currentUserMobile != null){
            currentUser = User(currentUserEmail,currentUserName,currentUserMobile,currentUserId)
        }
    }
}