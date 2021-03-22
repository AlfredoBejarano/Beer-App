package me.alfredobejarano.beerapp.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.alfredobejarano.beerapp.AppState
import me.alfredobejarano.beerapp.BuildConfig
import me.alfredobejarano.beerapp.datasource.local.BeerDao
import me.alfredobejarano.beerapp.datasource.local.DataBase
import me.alfredobejarano.beerapp.datasource.remote.PunkApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {
    @Volatile
    private var DATABASE_INSTANCE: DataBase? = null

    private val gson by lazy {
        GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    }

    private val gsonConverter by lazy {
        GsonConverterFactory.create(gson)
    }

    private val httpLoggingInterceptor by lazy {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .pingInterval(1, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    private val retrofitClient by lazy {
        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(gsonConverter)
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }

    /**
     * Creates an instance of the [DataBase] class.
     * @param ctx Context needed to create the Database instance.
     */
    private fun createBeerDataBaseInstance(ctx: Context): DataBase = Room
        .databaseBuilder(ctx, DataBase::class.java, "${BuildConfig.APPLICATION_ID}.db")
        .fallbackToDestructiveMigration()
        .build()

    /**
     * Returns the current singleton instance of the database.
     */
    private fun getDataBaseInstance(ctx: Context): DataBase =
        DATABASE_INSTANCE ?: synchronized(this) {
            DATABASE_INSTANCE ?: createBeerDataBaseInstance(ctx).also { DATABASE_INSTANCE = it }
        }

    /**
     * Injects the Singleton instance of [BeerDao] to any class that requests it.
     */
    @Provides
    @Singleton
    fun provideBeerDao(@ApplicationContext ctx: Context): BeerDao =
        getDataBaseInstance(ctx).provideBeerDao()

    /**
     * Injects the Singleton instance of [PunkApiService] to any class that requests it.
     */
    @Provides
    @Singleton
    fun providePunkApiService(): PunkApiService =
        retrofitClient.create(PunkApiService::class.java)

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext ctx: Context): SharedPreferences =
        ctx.getSharedPreferences("${BuildConfig.APPLICATION_ID}-prefs", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideAppState(): AppState = AppState
}