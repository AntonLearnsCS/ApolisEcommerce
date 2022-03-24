package com.example.ecommerceproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceproject.data.Product
import com.example.ecommerceproject.databinding.CheckoutItemViewHolderBinding
import com.example.ecommerceproject.viewholder.CheckoutItemViewholder

class CheckoutItemAdapter(val listProduct : MutableList<Product>) : RecyclerView.Adapter<CheckoutItemViewholder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckoutItemViewholder {
        val inflater = LayoutInflater.from(parent.context)
        val view = CheckoutItemViewHolderBinding.inflate(inflater, parent, false)
        return CheckoutItemViewholder(view)
    }

    override fun onBindViewHolder(holder: CheckoutItemViewholder, position: Int) {
        holder.bind(listProduct[position])
    }

    override fun getItemCount(): Int = listProduct.size


    fun submitList(newData : List<Product>){
        listProduct.clear()
        listProduct.addAll(newData)
        notifyDataSetChanged()
    }
}