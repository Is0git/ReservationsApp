package com.android.reservationapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.reservationapp.data.Order
import com.android.reservationapp.databinding.OrderListBinding

class OrderListAdapter :
    ListAdapter<Order, OrderListAdapter.MyViewHolder>(orderCallback) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderListAdapter.MyViewHolder {
        val binding: OrderListBinding =
            OrderListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderListAdapter.MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.order = item

    }


    class MyViewHolder(val binding: OrderListBinding) : RecyclerView.ViewHolder(binding.root)
}

val orderCallback: DiffUtil.ItemCallback<Order> = object : DiffUtil.ItemCallback<Order>() {
    override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean =
        oldItem.email.equals(newItem.email)

    override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean = oldItem == newItem
}