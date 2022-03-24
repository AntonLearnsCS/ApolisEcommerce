package com.example.ecommerceproject.activities.checkout

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ecommerceproject.data.Product
import com.example.ecommerceproject.data.database.CentralRepository
import com.example.ecommerceproject.data.database.LocalRepository

class CheckoutSharedViewModel (val userId : String, val centralRepository: CentralRepository) : ViewModel() {

        val listCheckoutProducts : LiveData<List<Product>> = centralRepository.getCartOrders(userId)
}

class CheckoutSharedViewModelFactory (val userId : String, val centralRepository: CentralRepository) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return CheckoutSharedViewModel(userId, centralRepository) as T
        }

}