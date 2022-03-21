package com.example.ecommerceproject.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceproject.data.Subcategory
import com.example.ecommerceproject.databinding.ViewPagerViewHolderBinding

import com.example.ecommerceproject.viewholder.ViewPagerHolder

class ViewPagerAdapter(val listSubCategory : List<Subcategory>) : RecyclerView.Adapter<ViewPagerHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ViewPagerViewHolderBinding.inflate(inflater,parent,false)
        return ViewPagerHolder(view)
    }

    override fun onBindViewHolder(holder: ViewPagerHolder, position: Int) {
        holder.bind(listSubCategory[position].subcategory_id)
        Log.i("tagViewPagerAdapter", "${listSubCategory[position].subcategory_id}")
    }

    override fun getItemCount(): Int = listSubCategory.size
}