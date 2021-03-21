package me.alfredobejarano.beerapp.utils

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.AnimRes

fun View?.startAnimationCompat(
    context: Context,
    @AnimRes resId: Int,
    onStart: () -> Unit = {},
    onEnd: () -> Unit = {},
    onRepeat: () -> Unit = {}
) {
    val animation = AnimationUtils.loadAnimation(context, resId)
    animation.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationEnd(animation: Animation?) = onEnd()
        override fun onAnimationStart(animation: Animation?) = onStart()
        override fun onAnimationRepeat(animation: Animation?) = onRepeat()
    })
    this?.startAnimation(animation) ?: Unit
}