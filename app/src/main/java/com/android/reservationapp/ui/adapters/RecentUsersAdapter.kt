package com.android.reservationapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.reservationapp.data.Users
import com.android.reservationapp.databinding.UsersListBinding


class RecentUsersAdapter :
    ListAdapter<Users, RecentUsersAdapter.MyViewHolder>(callback) {

    class MyViewHolder(val binding: UsersListBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = UsersListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.data = item
    }
}

val callback: DiffUtil.ItemCallback<Users>  = object : DiffUtil.ItemCallback<Users>() {
    override fun areItemsTheSame(oldItem: Users, newItem: Users): Boolean = oldItem.email == newItem.email

    override fun areContentsTheSame(oldItem: Users, newItem: Users): Boolean = oldItem == newItem
}
