package me.alfredobejarano.beerapp.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.alfredobejarano.beerapp.AppState
import me.alfredobejarano.beerapp.datasource.local.BeerDao
import me.alfredobejarano.beerapp.datasource.local.PaginationDataSource
import me.alfredobejarano.beerapp.datasource.remote.PunkApiService
import me.alfredobejarano.beerapp.model.local.Beer
import javax.inject.Inject

class BeerRepository @Inject constructor(
    private val beerDao: BeerDao,
    private val appState: AppState,
    private val beerApiService: PunkApiService,
    private val paginationDataSource: PaginationDataSource
) {
    private suspend fun getBeersFromApi(currentPageRequest: Int = 0): List<Beer> {

        val currentPage = if (currentPageRequest == 0) {
            paginationDataSource.getCurrentPage()
        } else {
            currentPageRequest
        }

        AppState.currentPage = currentPageRequest + 1
        paginationDataSource.saveCurrentPage(AppState.currentPage)

        return beerApiService.getBeers(currentPage).map { it.transform() }
    }

    suspend fun getBeers(currentPage: Int = 0): List<Beer> {
        val beers = if (appState.isConnected) {
            val remoteBeers = getBeersFromApi(currentPage)
            val localBeers = beerDao.getBeers() ?: emptyList()
            (remoteBeers + localBeers).sortedBy { it.id }
        } else {
            beerDao.getBeers() ?: emptyList()
        }

        if (appState.isConnected) {
            beers.forEach { beerDao.createOrUpdate(it) }
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

    suspend fun likeBeer(beer: Beer) =
        beerDao.createOrUpdate(beer.copy(liked = true, rejected = false))

    suspend fun rejectBeer(beer: Beer) =
        beerDao.createOrUpdate(beer.copy(rejected = true, liked = false))
}