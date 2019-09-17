package com.example.lwframework

import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

//fun  bindImageFromUrl(view: ImageView,imageUrl:String? ){
//
//
//    if(!imageUrl.isNullOrEmpty()){
//        Glide.with(view.context)
//            .load(imageUrl)
//            .transition(DrawableTransitionOptions.withCrossFade())
//            .addListener(null)
//            .into(view)
//    }
//}

private open class CustomListener:RequestListener<Drawable>{
    override fun onLoadFailed(
        e: GlideException?,
        model: Any?,
        target: Target<Drawable>?,
        isFirstResource: Boolean
    ): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onResourceReady(
        resource: Drawable?,
        model: Any?,
        target: Target<Drawable>?,
        dataSource: DataSource?,
        isFirstResource: Boolean
    ): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
const val TAG:String = "GlideHelper"
open class GlideHelper(){
    companion object {
        fun  bindImageFromUrl(view: ImageView,imageUrl:String? ){
            if(!imageUrl.isNullOrEmpty()){
                Glide.with(view.context)
                    .load(imageUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(view)
            }
        }
    }
}