package com.example.ecommerceproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceproject.data.Product
import com.example.ecommerceproject.databinding.SubcategoryItemViewHolderBinding
import com.example.ecommerceproject.viewholder.SubCategoryViewHolder

class SubCategoryItemAdapter(val listSubCategoryItem: MutableList<Product>) :
    RecyclerView.Adapter<SubCategoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubCategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = SubcategoryItemViewHolderBinding.inflate(inflater, parent, false)
        return SubCategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubCategoryViewHolder, position: Int) {
        holder.bind(listSubCategoryItem[position])
    }

    override fun getItemCount(): Int = listSubCategoryItem.size
}