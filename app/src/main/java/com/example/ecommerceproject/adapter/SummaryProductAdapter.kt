package com.example.ecommerceproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceproject.data.Product
import com.example.ecommerceproject.databinding.SummaryProductViewHolderBinding
import com.example.ecommerceproject.viewholder.SummaryProductViewHolder

class SummaryProductAdapter(val listProducts : List<Product>) : RecyclerView.Adapter<SummaryProductViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SummaryProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = SummaryProductViewHolderBinding.inflate(inflater,parent,false)
        return SummaryProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: SummaryProductViewHolder, position: Int) {
        holder.bind(listProducts[position])
    }

    override fun getItemCount(): Int = listProducts.size
}