package com.example.ecommerceproject.data.database

import androidx.lifecycle.LiveData
import com.example.ecommerceproject.data.*

class LocalRepository (private val database: EcommerceDatabase) {

    fun addProduct(product: Product){
        database.ecommerceDao.addProduct(product)
    }
    fun removeProduct(id : Int, productName : String){
        database.ecommerceDao.removeProduct(id, productName)
    }
    fun getCartProducts(userId : String) : LiveData<List<Product>>{
        return database.ecommerceDao.getCartProducts(userId)
    }
    fun getOrderedProducts(userId : String) : LiveData<List<Product>> {
        return database.ecommerceDao.getOrderedProducts(userId)
    }
    fun saveOrderedProduct(product: Product){
        return database.ecommerceDao.saveOrderedProduct(product)
    }
    fun getCurrentUser(userId : Int) : User {
        return database.ecommerceDao.getCurrentUser(userId)
    }
    fun saveUser(user: User){
        database.ecommerceDao.saveUser(user)
    }
    fun savePastOrders(product : List<Product>){
        database.ecommerceDao.savePastOrders(product)
    }
    fun getAddresses(userId : Int) : List<Address>{
        return database.ecommerceDao.getAddresses(userId)
    }
    fun saveAddress(address: Address){
        database.ecommerceDao.saveAddress(address)
    }
    fun saveCheckoutAddress(address: CurrentAddress){
        database.ecommerceDao.saveCheckoutAddress(address)
    }
    fun updateCreditCard(creditCard: CreditCard){
        database.ecommerceDao.saveCreditCard(creditCard)
    }
    fun getSelectedAddress() : CurrentAddress?{
        return database.ecommerceDao.getSelectedAddress()
    }
    fun saveCreditCard(creditCard: CreditCard){
        database.ecommerceDao.saveCreditCard(creditCard)
    }

    fun getSelectedCreditCard() : CreditCard?{
        return database.ecommerceDao.getSelectedCreditCard()
    }
}