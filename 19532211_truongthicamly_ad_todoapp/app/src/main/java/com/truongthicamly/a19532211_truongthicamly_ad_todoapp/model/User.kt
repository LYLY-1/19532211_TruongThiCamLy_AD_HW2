package com.truongthicamly.a19532211_truongthicamly_ad_todoapp.model

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(val congViec: String,val han: String ,val id:Int):Parcelable{
@Exclude
fun toMap(): Map<String, Any?> {
    return mapOf(
        "id" to id,
        "congViec" to congViec,
        "han" to han,
    )
}
}

