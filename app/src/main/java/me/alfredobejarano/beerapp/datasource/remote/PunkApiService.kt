package me.alfredobejarano.beerapp.datasource.remote

import me.alfredobejarano.beerapp.BuildConfig
import me.alfredobejarano.beerapp.model.remote.BeerResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PunkApiService {
    @GET("/v2/beers")
    suspend fun getBeers(
        @Query("page")
        page: Int = 1,
        @Query("per_page")
        pageSize: Int = BuildConfig.PAGE_SIZE
    ): List<BeerResponse>
}