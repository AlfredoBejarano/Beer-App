package me.alfredobejarano.beerapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.alfredobejarano.beerapp.databinding.ItemBeerFoodBinding

class FoodCompanyAdapter(private val items: List<String>) :
    RecyclerView.Adapter<FoodCompanyAdapter.FoodCompanyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        FoodCompanyViewHolder(ItemBeerFoodBinding.inflate(LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(holder: FoodCompanyViewHolder, position: Int) =
        holder.bind(items[position])

    override fun getItemCount() = items.size

    class FoodCompanyViewHolder(private val binding: ItemBeerFoodBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(name: String) {
            binding.foodName.text = name
        }
    }
}