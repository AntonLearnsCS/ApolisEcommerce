package com.example.ecommerceproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceproject.data.Product
import com.example.ecommerceproject.databinding.OrdersViewHolderBinding
import com.example.ecommerceproject.viewholder.OrdersViewHolder

class OrdersAdapter(val listOrders: MutableList<Product>) :
    RecyclerView.Adapter<OrdersViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = OrdersViewHolderBinding.inflate(inflater, parent, false)
        return OrdersViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
        holder.bind(listOrders[position])

        holder.binding.btnRemoveOrder.setOnClickListener {
            listOrders.remove(listOrders[position])
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int = listOrders.size

    fun submitList(newData: List<Product>) {
        listOrders.clear()
        listOrders.addAll(newData)
        notifyDataSetChanged()
    }
}