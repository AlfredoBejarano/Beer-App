package me.alfredobejarano.beerapp.widgets

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.MotionEvent
import androidx.cardview.widget.CardView
import me.alfredobejarano.beerapp.databinding.ItemBeerBinding
import me.alfredobejarano.beerapp.model.local.Beer

class BeerCard @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defaultStyleResource: Int = 0
) : CardView(context, attributeSet, defaultStyleResource) {
    private var dX = 0f
    private var dY = 0f

    private var originX = 0f
    private var originY = 0f

    private var displayWidth = 0

    private lateinit var beer: Beer

    var onSwipeLeft: Beer.() -> Unit = {}
    var onSwipeRight: Beer.() -> Unit = {}

    init {
        displayWidth = DisplayMetrics().apply {
            (context as? Activity)?.windowManager?.defaultDisplay?.getMetrics(this)
        }.widthPixels

        radius = 16f
        cardElevation = 2f
    }

    fun bind(beer: Beer) {
        this.beer = beer

        val beerLayout =
            ItemBeerBinding.inflate(LayoutInflater.from(context)).also { it.beer = beer }

        configureSwipe()

        addView(beerLayout.root)
    }

    private fun configureSwipe() {
        setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    originX = x
                    originY = y

                    dX = x - event.rawX
                    dY = y - event.rawY
                }
                MotionEvent.ACTION_MOVE -> animate()
                    .x(event.rawX + dX)
                    .y(event.rawY + dY)
                    .setDuration(0)
                    .start()
                MotionEvent.ACTION_UP -> {
                    when {
                        x >= (width / 2) -> beer.onSwipeRight()
                        x <= (-(width / 2)) -> beer.onSwipeLeft()
                        else -> {
                            performClick()
                            animate()
                                .x(originX)
                                .y(originY)
                                .setDuration(0)
                                .start()
                        }
                    }
                }
            }
            true
        }
    }
}