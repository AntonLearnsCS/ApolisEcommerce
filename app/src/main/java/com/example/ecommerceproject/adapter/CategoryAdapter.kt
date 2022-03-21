package com.example.ecommerceproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceproject.data.Category
import com.example.ecommerceproject.databinding.CategoryViewHolderBinding
import com.example.ecommerceproject.viewholder.CategoryViewHolder

class CategoryAdapter (val listCategory : List<Category>) : RecyclerView.Adapter<CategoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = CategoryViewHolderBinding.inflate(inflater,parent,false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(listCategory[position])
        holder.itemView.setOnClickListener {
            selectedCategoryLambda(listCategory[position])
        }
    }

    override fun getItemCount(): Int = listCategory.size

    private lateinit var selectedCategoryLambda : (Category) -> Unit

    fun setSelectedCategoryLambda(listener : (Category) -> Unit){
        selectedCategoryLambda = listener
    }

}