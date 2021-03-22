package me.alfredobejarano.beerapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import me.alfredobejarano.beerapp.repository.BeerRepository
import javax.inject.Inject

@HiltViewModel
class FavoriteBeersListViewModel @Inject constructor(private val repository: BeerRepository) :
    ViewModel() {
    fun getFavoriteBeers() = liveData(Dispatchers.IO) { emit(repository.getLikedBeers()) }
}