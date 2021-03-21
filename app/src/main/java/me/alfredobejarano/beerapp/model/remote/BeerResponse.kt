package me.alfredobejarano.beerapp.model.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import me.alfredobejarano.beerapp.model.TransformableModel
import me.alfredobejarano.beerapp.model.local.Beer

data class BeerResponse(
    @Expose
    @SerializedName("id")
    val id: Int? = 0,
    @Expose
    @SerializedName("name")
    val name: String? = "",
    @Expose
    @SerializedName("tagline")
    val tagLine: String? = "",
    @Expose
    @SerializedName("image_url")
    val imageUrl: String? = "",
    @Expose
    @SerializedName("description")
    val description: String? = "",
    @Expose
    @SerializedName("food_pairing")
    val foodPairing: List<String>? = emptyList()
) : TransformableModel<Beer> {
    override fun transform() = Beer(
        id = id ?: 0,
        name = name ?: "",
        tagLine = tagLine ?: "",
        imageUrl = imageUrl ?: "",
        description = description ?: "",
        liked = false,
        rejected = false,
        foodPairings = foodPairing ?: emptyList()
    )
}