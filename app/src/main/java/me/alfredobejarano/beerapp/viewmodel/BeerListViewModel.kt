package me.alfredobejarano.beerapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.alfredobejarano.beerapp.model.local.Beer
import me.alfredobejarano.beerapp.repository.BeerRepository
import javax.inject.Inject

@HiltViewModel
class BeerListViewModel @Inject constructor(private val beerRepository: BeerRepository) :
    ViewModel() {
    fun getBeerList(currentPage: Int = 0) =
        liveData(Dispatchers.IO) { emit(beerRepository.getBeers(currentPage)) }

    fun likeBeer(beer: Beer) = viewModelScope.launch(Dispatchers.IO) {
        beerRepository.likeBeer(beer)
    }

    fun rejectBeer(beer: Beer) = viewModelScope.launch(Dispatchers.IO) {
        beerRepository.rejectBeer(beer)
    }
}