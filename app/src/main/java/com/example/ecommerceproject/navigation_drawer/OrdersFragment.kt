package com.example.ecommerceproject.navigation_drawer

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.ecommerceproject.adapter.OrdersAdapter
import com.example.ecommerceproject.data.Product
import com.example.ecommerceproject.data.database.EcommerceDatabase
import com.example.ecommerceproject.databinding.FragmentOrdersBinding
import kotlinx.coroutines.runBlocking


class OrdersFragment : Fragment() {
    private lateinit var binding : FragmentOrdersBinding
    private lateinit var adapter : OrdersAdapter
    private lateinit var viewModel : NavigationSharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrdersBinding.inflate(layoutInflater, container, false)

        viewModel = ViewModelProvider(requireActivity()).get(NavigationSharedViewModel::class.java)
        loadOrders(binding.root.context)
        return binding.root
    }

    private fun loadOrders(context: Context) {

        val sPref = EncryptedSharedPreferences.create("login_settings", MasterKeys.getOrCreate(
            MasterKeys.AES256_GCM_SPEC),binding.root.context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)

        val currentUserId : String? = sPref.getString("currentUserId","0")

        currentUserId?.let {
            adapter = OrdersAdapter(mutableListOf<Product>())
            binding.rvListOrders.itemAnimator = null
            binding.rvListOrders.layoutManager = LinearLayoutManager(context)
            binding.rvListOrders.adapter = adapter

            viewModel.pastOrders.observe(viewLifecycleOwner, Observer {
                adapter.submitList(it)
            })
        }
    }

}