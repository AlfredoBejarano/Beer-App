package me.alfredobejarano.beerapp.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import me.alfredobejarano.beerapp.model.local.Beer
import me.alfredobejarano.beerapp.utils.startAnimationCompat

class BeerCardsListView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defaultStyleResource: Int = 0
) : FrameLayout(context, attributeSet, defaultStyleResource) {

    private var items: HashMap<Int, Beer> = hashMapOf()

    var onBeerLiked: Beer.() -> Unit = {}
    var onBeerRejected: Beer.() -> Unit = {}

    init {
        if (isInEditMode) {
            setBeers(listOf(Beer(name = "Beer", tagLine = "Nice drink")))
        }
    }

    fun setBeers(beers: List<Beer>) {
        beers.forEach {
            items[it.id] = it
        }

        addView(buildBeerCard(beers.first()))
    }

    private fun buildBeerCard(beer: Beer): BeerCard {
        val card = BeerCard(context)
        card.bind(beer)
        card.onSwipeLeft = {
            onBeerRejected()
            items.remove(beer.id)
            removeView(card)
            addLowerCard()
        }
        card.onSwipeRight = {
            onBeerLiked()
            items.remove(beer.id)
            removeView(card)
            addLowerCard()
        }

        return card
    }

    private fun addLowerCard() {
        if (items.size >= 1) {
            items.entries.first().run {
                val card = buildBeerCard(this.value)
                addView(card)
                card.startAnimationCompat(context, android.R.anim.fade_in)
            }
        }
    }
}