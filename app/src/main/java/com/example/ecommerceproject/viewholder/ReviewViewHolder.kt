package com.example.ecommerceproject.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceproject.data.Review
import com.example.ecommerceproject.databinding.CustomerReviewViewHolderBinding

class ReviewViewHolder (val binding : CustomerReviewViewHolderBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(review : Review){
        //binding.rbCustomerReview.rating = review.rating.toFloat()
        binding.tvCustomerName.text = review.full_name
        binding.tvReviewDescription.text = review.review
    }
}