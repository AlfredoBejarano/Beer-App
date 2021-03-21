package me.alfredobejarano.beerapp.view

import android.R.anim.fade_in
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import me.alfredobejarano.beerapp.R.anim.pop_up
import me.alfredobejarano.beerapp.databinding.FragmentBeerListBinding
import me.alfredobejarano.beerapp.utils.startAnimationCompat
import me.alfredobejarano.beerapp.utils.viewBinding

class BeerListFragment : Fragment() {
    private val binding by viewBinding(FragmentBeerListBinding::inflate)

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, state: Bundle?) =
        binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.navigationView.startAnimationCompat(context = requireContext(), resId = fade_in)

        binding.fabReject.startAnimationCompat(context = requireContext(), resId = pop_up, onEnd = {
            binding.fabUndo.visibility = View.VISIBLE
            binding.fabUndo.startAnimationCompat(
                context = requireContext(),
                resId = pop_up,
                onEnd = {
                    binding.fabLike.visibility = View.VISIBLE
                    binding.fabLike.startAnimationCompat(requireContext(), resId = pop_up)
                })
        })
    }
}