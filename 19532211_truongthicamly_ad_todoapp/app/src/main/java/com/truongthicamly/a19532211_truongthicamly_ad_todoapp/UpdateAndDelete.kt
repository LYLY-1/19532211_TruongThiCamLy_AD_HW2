package com.truongthicamly.a19532211_truongthicamly_ad_todoapp

interface UpdateAndDelete{
    fun modifyItem(itemUID : String , isDone : Boolean)
    fun onItemDelete(itemUID : String)
}