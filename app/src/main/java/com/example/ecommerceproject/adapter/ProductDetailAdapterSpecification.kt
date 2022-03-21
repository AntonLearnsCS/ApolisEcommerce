package com.example.ecommerceproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceproject.data.Specification
import com.example.ecommerceproject.databinding.SpecificationViewHolderBinding
import com.example.ecommerceproject.viewholder.ProductDetailSpecificationViewHolder

class ProductDetailAdapterSpecification(val listSpecification : List<Specification>) : RecyclerView.Adapter<ProductDetailSpecificationViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductDetailSpecificationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = SpecificationViewHolderBinding.inflate(inflater,parent,false)
        return ProductDetailSpecificationViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductDetailSpecificationViewHolder, position: Int) {
        holder.bind(listSpecification[position])
    }

    override fun getItemCount(): Int = listSpecification.size
}