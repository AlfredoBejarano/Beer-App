package me.alfredobejarano.beerapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.alfredobejarano.beerapp.databinding.ItemBeerGridBinding
import me.alfredobejarano.beerapp.model.local.Beer

class FavoriteBeerAdapter(
    private val items: List<Beer>,
    private val onBeerClicked: Beer.() -> Unit
) : RecyclerView.Adapter<FavoriteBeerAdapter.FavoriteBeerViewHolder>() {

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FavoriteBeerViewHolder(
        ItemBeerGridBinding.inflate(LayoutInflater.from(parent.context)), onBeerClicked
    )

    override fun onBindViewHolder(holder: FavoriteBeerViewHolder, position: Int) =
        holder.bind(items[position])

    class FavoriteBeerViewHolder(
        private val binding: ItemBeerGridBinding,
        private val onBeerClicked: Beer.() -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(beer: Beer) {
            binding.beer = beer
            binding.beerPicture.setImageURI(beer.imageUrl)
            binding.executePendingBindings()
            binding.root.setOnClickListener { beer.onBeerClicked() }
        }
    }
}