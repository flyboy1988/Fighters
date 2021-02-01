package com.flyboy.fighters.model

import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.databinding.BindingAdapter
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop


@Entity(tableName = "fighter")
data class Fighter
    (
    @NonNull
    @PrimaryKey
    val id: Int,
       val nickName: String?,
    val name: String?,
       val country: String?,
    val role: String?,
    val manufacturer: String?,
    val description: String?,
    val poster_path: String,
 )


    @BindingAdapter("android:loadFighterImage")
   fun loadFighterImage(imageView: ImageView,poster_path: String?){
       Glide.with(imageView)
           .load(poster_path)
           .transform(CircleCrop())
           .into(imageView)
}
