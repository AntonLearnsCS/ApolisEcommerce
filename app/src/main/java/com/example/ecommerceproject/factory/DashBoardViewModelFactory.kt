package com.example.ecommerceproject.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ecommerceproject.data.database.EcommerceDatabase
import com.example.ecommerceproject.viewmodel.DashBoardViewModel

class DashBoardViewModelFactory(private val ecommerceDatabase: EcommerceDatabase) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashBoardViewModel::class.java)) {
            return DashBoardViewModel(ecommerceDatabase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}