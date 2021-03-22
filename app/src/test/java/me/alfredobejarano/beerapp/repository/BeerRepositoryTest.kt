package me.alfredobejarano.beerapp.repository

import kotlinx.coroutines.runBlocking
import me.alfredobejarano.beerapp.AppState
import me.alfredobejarano.beerapp.datasource.local.BeerDao
import me.alfredobejarano.beerapp.datasource.local.PaginationDataSource
import me.alfredobejarano.beerapp.datasource.remote.PunkApiService
import me.alfredobejarano.beerapp.model.local.Beer
import me.alfredobejarano.beerapp.model.remote.BeerResponse
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BeerRepositoryTest {

    @Mock
    private lateinit var mockAppState: AppState

    @Mock
    private lateinit var mockBeerDao: BeerDao

    @Mock
    private lateinit var mockBeerApiService: PunkApiService

    @Mock
    private lateinit var mockPaginationDataSource: PaginationDataSource

    private lateinit var testCandidate: BeerRepository

    private lateinit var mocks: AutoCloseable

    private val mockLocalBeers = List(5) {
        Beer(id = it)
    }

    private val mockRemoteBeers = List(5) {
        BeerResponse(id = it)
    }

    private val mockLikedBeers = List(1) {
        Beer(id = it, liked = true)
    }

    private val mockRejectedBeers = List(1) {
        Beer(id = it + 1, rejected = true)
    }

    @Before
    fun setUp() {
        mocks = MockitoAnnotations.openMocks(this::class)
        testCandidate =
            BeerRepository(mockBeerDao, mockAppState, mockBeerApiService, mockPaginationDataSource)
    }

    @After
    fun tearDown() {
        mocks.close()
    }

    @Test
    fun getBeers_whenConnected() {
        runBlocking {
            Mockito.`when`(mockBeerDao.createOrUpdate(Beer())).thenReturn(Unit)
            Mockito.`when`(mockAppState.isConnected).thenReturn(true)
            Mockito.`when`(mockPaginationDataSource.getCurrentPage()).thenReturn(1)
            Mockito.`when`(mockBeerApiService.getBeers(1)).thenReturn(mockRemoteBeers)
            Mockito.`when`(mockBeerDao.getBeers()).thenReturn(mockLocalBeers)
            Mockito.`when`(mockBeerDao.getLikedBeers()).thenReturn(mockLikedBeers)
            Mockito.`when`(mockBeerDao.getRejectedBeers()).thenReturn(mockRejectedBeers)

            val beers = testCandidate.getBeers()

            beers.forEach { println(it.toString()) }

            assert(beers.size == 3)
        }
    }

    @Test
    fun getBeers_whenDisconnected() {
        runBlocking {
            Mockito.`when`(mockAppState.isConnected).thenReturn(false)

            Mockito.`when`(mockBeerDao.getBeers()).thenReturn(mockLocalBeers)
            Mockito.`when`(mockBeerDao.getLikedBeers()).thenReturn(mockLikedBeers)
            Mockito.`when`(mockBeerDao.getRejectedBeers()).thenReturn(mockRejectedBeers)

            val beers = testCandidate.getBeers()

            beers.forEach { println(it.toString()) }

            assert(beers.size == 3)
        }
    }
}