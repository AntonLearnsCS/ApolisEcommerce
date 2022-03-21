package com.example.ecommerceproject.data.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.ecommerceproject.data.Product
import com.example.ecommerceproject.data.User

@Dao
interface EcommerceDao {

    @Insert(onConflict = REPLACE)
    fun addProduct(product: Product)

    @Query("DELETE FROM Product WHERE user_id = :id AND product_name = :productName")
    fun removeProduct(id : Int, productName : String)

    @Query("SELECT * FROM Product WHERE user_id = :userId AND is_purchased = 0")
    fun getCartProducts(userId : String) : List<Product>

    @Query("SELECT * FROM Product WHERE user_id = :userId AND is_purchased = 1")
    fun getOrderedProducts(userId : String) : List<Product>

    @Query("SELECT * FROM User WHERE user_id = :userId")
    fun getCurrentUser(userId : Int) : User

    @Insert(onConflict = REPLACE)
    fun saveUser(user: User)

    @Insert
    fun savePastOrders(product : List<Product>)

}

