package com.funckyhacker.capofiler.view.search

import android.animation.Animator
import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewTreeObserver
import android.view.animation.DecelerateInterpolator

abstract class RevealActivity : AppCompatActivity() {

    companion object {
        private const val DEFAULT_ANIMATION_DURATION = 500
    }

    private var reverseRevealStarted: Boolean = false

    private val rootView: View?
        get() {
            val window = window
            return window?.decorView
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            startReveal()
        }
    }

    abstract fun getRevealCenterPoint(): Point

    private fun startReveal() {
        val rootLayout = rootView ?: return
        rootLayout.visibility = View.INVISIBLE
        val viewTreeObserver = rootLayout.viewTreeObserver
        if (!viewTreeObserver.isAlive) {
            return
        }
        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onGlobalLayout() {
                circularRevealActivity(rootLayout, false)
                rootLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }

    private fun reverseReveal() {
        val rootLayout = rootView ?: return
        val circularReveal = circularRevealActivity(rootLayout, true)
        reverseRevealStarted = true
        circularReveal.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {

            }

            override fun onAnimationEnd(animator: Animator) {
                rootLayout.visibility = View.INVISIBLE
                super@RevealActivity.finish()
                reverseRevealStarted = false

            }

            override fun onAnimationCancel(animator: Animator) {

            }

            override fun onAnimationRepeat(animator: Animator) {

            }
        })
    }

    private fun circularRevealActivity(rootLayout: View, reverse: Boolean): Animator {
        val point = getRevealCenterPoint()
        val cx = (rootLayout.width * point.x).toInt()
        val cy = (rootLayout.height * point.y).toInt()
        val finalRadius = Math.sqrt(Math.pow(rootLayout.width.toDouble(), 2.0) + Math.pow(rootLayout.height.toDouble(), 2.0)).toFloat()
        val animator: Animator
        animator = ViewAnimationUtils.createCircularReveal(rootLayout, cx, cy, if (reverse) finalRadius else 0f, if (reverse) 0f else finalRadius)
        animator.duration = DEFAULT_ANIMATION_DURATION.toLong()
        animator.interpolator = DecelerateInterpolator()
        rootLayout.visibility = View.VISIBLE
        animator.start()
        return animator
    }

    override fun finish() {
        if (!reverseRevealStarted) {
            reverseReveal()
        }
    }

    class Point(val x: Float, val y: Float)
}
