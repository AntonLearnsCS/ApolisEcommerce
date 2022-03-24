package com.example.ecommerceproject.navigation_drawer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ecommerceproject.data.Product
import com.example.ecommerceproject.data.database.CentralRepository

class NavigationSharedViewModel (val userId : String, val centralRepository: CentralRepository) : ViewModel() {

     val cartOrders : LiveData<List<Product>> = centralRepository.getCartOrders(userId)

     val pastOrders : LiveData<List<Product>> = centralRepository.getPastOrders(userId)
}


class NavigationSharedViewModelFactory (val userId : String, val centralRepository: CentralRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NavigationSharedViewModel(userId, centralRepository) as T
    }

}