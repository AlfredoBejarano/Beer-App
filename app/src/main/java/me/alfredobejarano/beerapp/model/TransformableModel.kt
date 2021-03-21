package me.alfredobejarano.beerapp.model

interface TransformableModel<L> {
    fun transform(): L
}