package com.example.ecommerceproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceproject.data.Review
import com.example.ecommerceproject.databinding.CustomerReviewViewHolderBinding
import com.example.ecommerceproject.viewholder.ReviewViewHolder

class CustomerReviewAdapter (val listReviews : List<Review>): RecyclerView.Adapter<ReviewViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = CustomerReviewViewHolderBinding.inflate(inflater, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(listReviews[position])
    }

    override fun getItemCount(): Int = listReviews.size
}