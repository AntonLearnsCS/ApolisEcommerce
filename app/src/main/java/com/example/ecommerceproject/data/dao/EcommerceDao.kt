package com.example.ecommerceproject.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.ecommerceproject.data.*

@Dao
interface EcommerceDao {

    @Insert(onConflict = REPLACE)
    fun addProduct(product: Product)

    @Query("DELETE FROM Product WHERE user_id = :id AND product_name = :productName")
    fun removeProduct(id : Int, productName : String)

    @Query("SELECT * FROM Product WHERE user_id = :userId AND is_purchased = 0")
    fun getCartProducts(userId : String) : LiveData<List<Product>>

    @Query("SELECT * FROM Product WHERE user_id = :userId AND is_purchased = 1")
    fun getOrderedProducts(userId : String) : LiveData<List<Product>>

    @Insert(onConflict = REPLACE)
    fun saveOrderedProduct(product: Product)

    @Query("SELECT * FROM User WHERE user_id = :userId")
    fun getCurrentUser(userId : Int) : User

    @Insert(onConflict = REPLACE)
    fun saveUser(user: User)

    @Insert
    fun savePastOrders(product : List<Product>)

    @Query("SELECT * FROM address WHERE user_id = :userId")
    fun getAddresses(userId : Int) : List<Address>

    @Insert
    fun saveAddress(address: Address)

    @Insert(onConflict = REPLACE)
    fun saveCheckoutAddress(address: CurrentAddress)

    @Update
    fun updateCreditCard(creditCard: CreditCard)

    @Query("SELECT * FROM CurrentAddress")
    fun getSelectedAddress() : CurrentAddress?

    @Insert(onConflict = REPLACE)
    fun saveCreditCard(creditCard: CreditCard)

    @Query("SELECT * FROM CreditCard")
    fun getSelectedCreditCard() : CreditCard?

}

