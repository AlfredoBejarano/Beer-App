package me.alfredobejarano.beerapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import me.alfredobejarano.beerapp.model.local.Beer
import me.alfredobejarano.beerapp.repository.BeerRepository

class BeerListViewModel constructor(private val beerRepository: BeerRepository) : ViewModel() {
    fun getBeerList() = liveData(Dispatchers.IO) { emit(beerRepository.getBeers()) }

    fun likeBeer(beer: Beer) = liveData(Dispatchers.IO) {
        beerRepository.likeBeer(beer)
        emit(beer)
    }

    fun rejectBeer(beer: Beer) = liveData(Dispatchers.IO) {
        beerRepository.rejectBeer(beer)
        emit(beer)
    }

    fun redoAction(beer: Beer) = liveData(Dispatchers.IO) {
        beerRepository.insertBeer(beer)
        emit(beer)
    }
}