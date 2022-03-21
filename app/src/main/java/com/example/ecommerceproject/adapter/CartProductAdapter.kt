package com.example.ecommerceproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceproject.data.Product
import com.example.ecommerceproject.databinding.CartProductViewHolderBinding
import com.example.ecommerceproject.viewholder.CartProductViewHolder

class CartProductAdapter(val listCartProduct : MutableList<Product>) : RecyclerView.Adapter<CartProductViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = CartProductViewHolderBinding.inflate(inflater, parent, false)
        return CartProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartProductViewHolder, position: Int) {
        holder.bind(listCartProduct[position])

        holder.binding.btnRemoveFromCart.setOnClickListener {
            listCartProduct.removeAt(position)
            notifyItemChanged(position)
        }

        holder.binding.btnCartOrderPlus.setOnClickListener {
            if (this::quantityIncreasedChangedListener.isInitialized) {
                quantityIncreasedChangedListener(listCartProduct[position].price.toFloat())
            }
        }

        holder.binding.btnCartOrderMinus.setOnClickListener {
            if (this::quantityDecreasedChangedListener.isInitialized && holder.binding.tvCartItemCount.text.toString().toInt() > 0) {
                quantityDecreasedChangedListener(listCartProduct[position].price.toFloat())
            }
        }
        holder.binding.btnRemoveFromCart.setOnClickListener {
            if(this::removeProductListener.isInitialized){
                removeProductListener(listCartProduct[position].quantity * listCartProduct[position].price.toFloat())
            }
        }
    }

    override fun getItemCount(): Int = listCartProduct.size

    private lateinit var quantityDecreasedChangedListener : (Float) -> Unit

    fun setQuantityDecreasedListener(listener : (Float) -> Unit){
        quantityDecreasedChangedListener = listener
    }

    private lateinit var quantityIncreasedChangedListener : (Float) -> Unit

    fun setQuantityIncreasedListener(listener : (Float) -> Unit){
        quantityIncreasedChangedListener = listener
    }

    private lateinit var removeProductListener : (Float) -> Unit

    fun setRemoveProductListener(listener: (Float) -> Unit){
        removeProductListener = listener
    }
}