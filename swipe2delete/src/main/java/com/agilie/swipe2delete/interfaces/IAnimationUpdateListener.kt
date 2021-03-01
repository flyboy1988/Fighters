package com.flyboy.fighters.swipe2delete.interfaces

import com.flyboy.fighters.swipe2delete.ModelOptions


interface IAnimationUpdateListener {
    fun onAnimationUpdated(animation: android.animation.ValueAnimator?, options: ModelOptions<*>)
}