package com.sinhro.memesapp_surf.ui

import android.content.Context
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View


class SwipeListener : View.OnTouchListener{
    private var gestureDetector: GestureDetector? = null


    fun OnSwipeTouchListener(
        ctx: Context?,
        onSwipeDown : () -> Unit,
        onSwipeRight : () -> Unit,
        onSwipeLeft :() -> Unit,
        onSwipeUp : () -> Unit
    ) {
        gestureDetector = GestureDetector(ctx, GestureListener(
            onSwipeDown,
            onSwipeRight,
            onSwipeLeft,
            onSwipeUp
        ))
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        return gestureDetector!!.onTouchEvent(event)
    }

    private class GestureListener(
        onSwipeDown : () -> Unit,
        onSwipeRight : () -> Unit,
        onSwipeLeft :() -> Unit,
        onSwipeUp : () -> Unit
    ) : SimpleOnGestureListener() {
        private lateinit var onSwipeDown : () -> Unit
        private lateinit var onSwipeLeft : () -> Unit
        private lateinit var onSwipeRight : () -> Unit
        private lateinit var onSwipeUp: () -> Unit

        companion object {
            private const val SWIPE_THRESHOLD = 400
            private const val SWIPE_VELOCITY_THRESHOLD = 100
        }

        init {
            this.onSwipeDown = onSwipeDown
            this.onSwipeLeft = onSwipeLeft
            this.onSwipeRight = onSwipeRight
            this.onSwipeUp = onSwipeUp
        }


        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onFling(
            e1: MotionEvent,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            var result = false
            try {
                val diffY = e2.y - e1.y
                val diffX = e2.x - e1.x
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(
                            velocityX
                        ) > SWIPE_VELOCITY_THRESHOLD
                    ) {
                        if (diffX > 0) {
                            onSwipeRight.invoke()
                        } else {
                            onSwipeLeft.invoke()
                        }
                        result = true
                    }
                } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(
                        velocityY
                    ) > SWIPE_VELOCITY_THRESHOLD
                ) {
                    if (diffY > 0) {
                        onSwipeDown.invoke()
                    } else {
                        onSwipeUp.invoke()
                    }
                    result = true
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
            return result
        }


    }

}