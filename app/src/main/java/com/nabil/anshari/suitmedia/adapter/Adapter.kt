package com.nabil.anshari.suitmedia.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nabil.anshari.suitmedia.databinding.UserItemBinding
import com.nabil.anshari.suitmedia.response.UserData

class Adapter : RecyclerView.Adapter<Adapter.ItemViewHolder>() {

    private val userList = ArrayList<UserData>()
    private var onClickCallback: OnItemClickCallback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = UserItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  ItemViewHolder((view))
    }

    override fun getItemCount(): Int = userList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    inner class ItemViewHolder(private val itemBinding: UserItemBinding): RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(data: UserData) {
            itemBinding.root.setOnClickListener{
                onClickCallback?.onItemClicked(data)
            }
            itemBinding.apply {
                tvFirstName.text = data.firstName
                tvLastName.text = data.lastName
                tvEmailUser.text = data.email
                Glide.with(itemView)
                    .load(data.avatar)
                    .circleCrop()
                    .into(profileUser)
            }
        }
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: UserData)
    }

    fun setClickCallback(itemClickCallback: OnItemClickCallback){
        this.onClickCallback = itemClickCallback
    }
    @SuppressLint("NotifyDataSetChanged")
    fun clearUsers() {
        this.userList.clear()
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(users:ArrayList<UserData>){
        userList.clear()
        userList.addAll(users)
        notifyDataSetChanged()
    }
}