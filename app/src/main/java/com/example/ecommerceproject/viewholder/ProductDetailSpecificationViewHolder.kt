package com.example.ecommerceproject.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceproject.data.Specification
import com.example.ecommerceproject.databinding.SpecificationViewHolderBinding

class ProductDetailSpecificationViewHolder (val binding : SpecificationViewHolderBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(specification: Specification){
        binding.tvTitle.text = specification.title
        binding.tvSpecification.text = specification.specification
    }
}