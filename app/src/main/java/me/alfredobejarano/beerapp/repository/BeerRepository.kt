package me.alfredobejarano.beerapp.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.alfredobejarano.beerapp.AppState
import me.alfredobejarano.beerapp.datasource.local.BeerDao
import me.alfredobejarano.beerapp.datasource.local.PaginationDataSource
import me.alfredobejarano.beerapp.datasource.remote.PunkApiService
import me.alfredobejarano.beerapp.model.local.Beer

class BeerRepository constructor(
    private val beerDao: BeerDao,
    private val appState: AppState,
    private val beerApiService: PunkApiService,
    private val paginationDataSource: PaginationDataSource
) {
    private suspend fun getBeersFromApi(): List<Beer> {
        val currentPage = paginationDataSource.getCurrentPage()
        AppState.currentPage = currentPage
        return beerApiService.getBeers(currentPage).map { it.transform() }
    }

    suspend fun getBeers(): List<Beer> {
        val beers = if (appState.isConnected) {
            (getBeersFromApi() + (beerDao.getBeers() ?: emptyList())).sortedBy { it.id }
        } else {
            beerDao.getBeers() ?: emptyList()
        }

        val likedBeers = getLikedBeers()
        val rejectedBeers = beerDao.getRejectedBeers()

        return withContext(Dispatchers.Default) {
            val filteredBeers: HashMap<Int, Beer> =
                beers.associateBy({ it.id }, { it }) as HashMap<Int, Beer>

            likedBeers?.forEach { filteredBeers.remove(it.id) }
            rejectedBeers?.forEach { filteredBeers.remove(it.id) }

            filteredBeers.values.toList()
        }
    }

    suspend fun getLikedBeers() = beerDao.getLikedBeers()

    suspend fun insertBeer(beer: Beer) = beerDao.createOrUpdate(beer)

    suspend fun likeBeer(beer: Beer) =
        beerDao.createOrUpdate(beer.copy(liked = true, rejected = false))

    suspend fun rejectBeer(beer: Beer) =
        beerDao.createOrUpdate(beer.copy(rejected = true, liked = false))
}