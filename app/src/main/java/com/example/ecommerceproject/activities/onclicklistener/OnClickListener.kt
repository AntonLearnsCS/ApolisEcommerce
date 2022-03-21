package com.example.ecommerceproject.activities.onclicklistener

import com.example.ecommerceproject.data.Product

interface DecreasedQuantityTest{
    fun decreasedQuantity(price : Float)
}

interface IncreaseQuantityTest{
    fun increaseQuantity(price: Float)
}

interface RemoveProductTest {
    fun removeProductTest(product: Product, position : Int)
}
