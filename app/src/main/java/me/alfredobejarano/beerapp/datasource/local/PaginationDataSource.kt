package me.alfredobejarano.beerapp.datasource.local

import android.content.SharedPreferences
import androidx.core.content.edit

class PaginationDataSource(private val sharedPreferences: SharedPreferences) {
    fun getCurrentPage() = sharedPreferences.getInt(PAGE_SHARED_PREFS_KEY, DEFAULT_PAGE)

    fun saveCurrentPage(newPage: Int) = sharedPreferences.edit {
        putInt(PAGE_SHARED_PREFS_KEY, newPage)
    }

    private companion object {
        const val DEFAULT_PAGE = 1
        const val PAGE_SHARED_PREFS_KEY = "current_page"
    }
}