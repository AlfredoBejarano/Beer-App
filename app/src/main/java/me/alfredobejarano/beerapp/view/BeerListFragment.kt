package me.alfredobejarano.beerapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import me.alfredobejarano.beerapp.AppState
import me.alfredobejarano.beerapp.R
import me.alfredobejarano.beerapp.R.anim.pop_up
import me.alfredobejarano.beerapp.databinding.FragmentBeerListBinding
import me.alfredobejarano.beerapp.utils.startAnimationCompat
import me.alfredobejarano.beerapp.utils.viewBinding
import me.alfredobejarano.beerapp.viewmodel.BeerListViewModel

@AndroidEntryPoint
class BeerListFragment : Fragment() {
    private val viewModel: BeerListViewModel by viewModels()
    private val binding by viewBinding(FragmentBeerListBinding::inflate)

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, state: Bundle?) =
        binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabReject.startAnimationCompat(context = requireContext(), resId = pop_up, onEnd = {
            binding.fabLike.visibility = View.VISIBLE
            binding.fabLike.startAnimationCompat(requireContext(), resId = pop_up)
        })

        binding.fabLike.setOnClickListener { binding.beersList.likeCurrentCard() }
        binding.fabReject.setOnClickListener { binding.beersList.rejectCurrentCard() }

        binding.beersList.onCardClicked = {
            findNavController().navigate(BeerListFragmentDirections.showBeerDetails(this))
        }

        binding.beersList.onBeerLiked = {
            viewModel.likeBeer(this)
            showActionPopUp(R.drawable.ic_baseline_like)
        }

        binding.beersList.onBeerRejected = {
            viewModel.rejectBeer(this)
            showActionPopUp(R.drawable.ic_baseline_reject)
        }

        binding.beersList.onLastBeerSwiped = { getBeers() }

        getBeers()
    }

    private fun getBeers() {
        binding.progress.visibility = View.VISIBLE
        viewModel.getBeerList(AppState.currentPage).observe(viewLifecycleOwner, { beers ->
            beers?.run(binding.beersList::setBeers)
            binding.progress.visibility = View.GONE
        })
    }

    private fun showActionPopUp(@DrawableRes icon: Int) = binding.imageViewAction.run {
        setImageResource(icon)
        visibility = View.VISIBLE
        startAnimationCompat(requireContext(), resId = pop_up, onEnd = {
            binding.imageViewAction.visibility = View.INVISIBLE
        })
    }
}