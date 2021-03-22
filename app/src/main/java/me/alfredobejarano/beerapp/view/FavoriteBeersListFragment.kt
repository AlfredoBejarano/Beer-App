package me.alfredobejarano.beerapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import me.alfredobejarano.beerapp.adapter.FavoriteBeerAdapter
import me.alfredobejarano.beerapp.databinding.FragmentFavoriteBeersListBinding
import me.alfredobejarano.beerapp.utils.viewBinding
import me.alfredobejarano.beerapp.view.FavoriteBeersListFragmentDirections.Companion.showDetails
import me.alfredobejarano.beerapp.viewmodel.FavoriteBeersListViewModel

@AndroidEntryPoint
class FavoriteBeersListFragment : Fragment() {
    private val viewModel: FavoriteBeersListViewModel by viewModels()
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

        viewModel.getFavoriteBeers().observe(viewLifecycleOwner, { beers ->
            beers?.run {
                binding.favoriteBeersList.adapter = FavoriteBeerAdapter(this) {
                    findNavController().navigate(showDetails(this))
                }
            }
        })
    }

    private companion object {
        const val GRID_SPAN_COUNT = 2
    }
}