package com.example.ecommerceproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceproject.activities.onclicklistener.DecreasedQuantityTest
import com.example.ecommerceproject.activities.onclicklistener.IncreaseQuantityTest
import com.example.ecommerceproject.activities.onclicklistener.RemoveProductTest
import com.example.ecommerceproject.data.Product
import com.example.ecommerceproject.databinding.CartProductViewHolderBinding
import com.example.ecommerceproject.viewholder.CartProductViewHolder

class CartProductAdapter(
    val listCartProduct: MutableList<Product>,
    val decreasedQuantityTest: DecreasedQuantityTest,
    val increaseQuantityTest: IncreaseQuantityTest,
    val removeProductTest: RemoveProductTest
) : RecyclerView.Adapter<CartProductViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = CartProductViewHolderBinding.inflate(inflater, parent, false)
        return CartProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartProductViewHolder, position: Int) {
        holder.bind(listCartProduct[position], decreasedQuantityTest, increaseQuantityTest, removeProductTest, position)
        holder.binding.btnRemoveFromCart.setOnClickListener {

            onRemoveClicked(listCartProduct[position])

            listCartProduct.removeAt(position)
            notifyItemChanged(position)

        }
    }


    override fun getItemCount(): Int = listCartProduct.size

    private lateinit var onRemoveClicked : (Product) -> Unit

    fun setOnRemoveClicked(listener : (Product) -> Unit){
        onRemoveClicked = listener
    }
}