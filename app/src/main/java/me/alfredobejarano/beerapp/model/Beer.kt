package me.alfredobejarano.beerapp.model

data class Beer(
    val id: Int? = 0,
    val name: String? = "",
    val tagline: String? = "",
    val imageUrl: String? = "",
    val description: String? = "",
    val foodPairing: List<String>? = emptyList()
)