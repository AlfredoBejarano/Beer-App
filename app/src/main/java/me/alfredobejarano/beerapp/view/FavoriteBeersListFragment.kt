package me.alfredobejarano.beerapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import me.alfredobejarano.beerapp.adapter.FavoriteBeerAdapter
import me.alfredobejarano.beerapp.databinding.FragmentFavoriteBeersListBinding
import me.alfredobejarano.beerapp.model.local.Beer
import me.alfredobejarano.beerapp.utils.viewBinding

class FavoriteBeersListFragment : Fragment() {
    private val binding by viewBinding(FragmentFavoriteBeersListBinding::inflate)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.favoriteBeersList.layoutManager =
            GridLayoutManager(requireContext(), GRID_SPAN_COUNT)

        binding.favoriteBeersList.adapter = FavoriteBeerAdapter(
            List(5) {
                Beer(
                    id = it,
                    name = "Beer $it",
                    tagLine = "Nice drink",
                    description = "Nice Drink",
                    foodPairings = listOf("Nice Food", "Nice Food")
                )
            }
        ) {
            findNavController().navigate(FavoriteBeersListFragmentDirections.showDetails(this))
        }
    }

    private companion object {
        const val GRID_SPAN_COUNT = 2
    }
}