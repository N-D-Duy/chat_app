package com.example.userlogin.adapter

import android.content.Context
import android.graphics.Bitmap
import android.media.Image
import android.provider.MediaStore
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import java.net.URL


class ConvertImage(context: Context) {
    private val mContext = context
    fun imageToUrl(image: Bitmap): String? {
        return MediaStore.Images.Media.insertImage(
            mContext.contentResolver, image, "title", null
        )
    }

    fun urlToImage(imageUrl: String, imageView: ImageView) {
        Glide.with(mContext)
            .asBitmap()
            .load(imageUrl)
            .into(object : SimpleTarget<Bitmap?>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap?>?
                ) {
                    imageView.setImageBitmap(resource)
                }
            })
    }
}