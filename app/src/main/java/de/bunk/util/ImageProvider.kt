package de.bunk.util

import android.widget.ImageView
import com.bumptech.glide.Glide

class ImageProvider {
    class Builder {
        private lateinit var url: String

        fun url(url: String): Builder {
            this.url = url
            return this
        }

        fun into(imageView: ImageView) {
            Glide.with(imageView).load(url).into(imageView)
        }
    }
}