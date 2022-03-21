package com.example.ecommerceproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceproject.data.User
import com.example.ecommerceproject.data.database.EcommerceDatabase
import kotlinx.coroutines.launch

class DashBoardViewModel(val ecommerceDatabase: EcommerceDatabase) : ViewModel() {
    val _currentUser = MutableLiveData<User?>()
    val currentUser : LiveData<User?>
    get() = _currentUser

    fun getCurrentUser(id : Int){
        viewModelScope.launch {
           val result = ecommerceDatabase.ecommerceDao.getCurrentUser(id)
            if (!result.user_id.isEmpty()){
                _currentUser.value = result
            }
        }
    }
}