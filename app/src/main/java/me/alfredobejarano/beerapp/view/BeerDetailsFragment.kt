package me.alfredobejarano.beerapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import me.alfredobejarano.beerapp.adapter.FoodCompanyAdapter
import me.alfredobejarano.beerapp.databinding.FragmentBeerDetailsBinding

@AndroidEntryPoint
class BeerDetailsFragment : Fragment() {
    private val args: BeerDetailsFragmentArgs by navArgs()

    private val binding by lazy {
        FragmentBeerDetailsBinding.inflate(LayoutInflater.from(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.beer = args.beer
        binding.imageViewBeer.setImageURI(args.beer.imageUrl)
        binding.executePendingBindings()

        binding.recyclerViewFood.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewFood.adapter = FoodCompanyAdapter(args.beer.foodPairings)
    }
}