package com.truongthicamly.a19532211_truongthicamly_ad_todoapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.truongthicamly.a19532211_truongthicamly_ad_todoapp.databinding.ItemUserBinding
import com.truongthicamly.a19532211_truongthicamly_ad_todoapp.model.User

class RecycleAdapter(private val usersList :List<User>) : RecyclerView.Adapter<MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(usersList[position])
    }
    override fun getItemCount(): Int = usersList.size
}

class MyViewHolder(private val binding :ItemUserBinding ): RecyclerView.ViewHolder(binding.root){

    fun bind(user : User){
        binding.tvTitle.text = user.congViec
        binding.tvTime.text = user.han
    }

}