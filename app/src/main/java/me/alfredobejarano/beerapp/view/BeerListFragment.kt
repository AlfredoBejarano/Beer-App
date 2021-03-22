package me.alfredobejarano.beerapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import me.alfredobejarano.beerapp.R
import me.alfredobejarano.beerapp.R.anim.pop_up
import me.alfredobejarano.beerapp.databinding.FragmentBeerListBinding
import me.alfredobejarano.beerapp.model.local.Beer
import me.alfredobejarano.beerapp.utils.startAnimationCompat
import me.alfredobejarano.beerapp.utils.viewBinding

class BeerListFragment : Fragment() {
    private val binding by viewBinding(FragmentBeerListBinding::inflate)

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, state: Bundle?) =
        binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabReject.startAnimationCompat(context = requireContext(), resId = pop_up, onEnd = {
            binding.fabLike.visibility = View.VISIBLE
            binding.fabLike.startAnimationCompat(requireContext(), resId = pop_up)
        })

        binding.beersList.setBeers(
            List(5) {
                Beer(id = it, name = "Beer $it", tagLine = "Nice drink")
            }
        )

        binding.beersList.onBeerLiked = {
            showActionPopUp(R.drawable.ic_baseline_like)
        }

        binding.beersList.onBeerRejected = {
            showActionPopUp(R.drawable.ic_baseline_reject)
        }

        binding.beersList.onLastBeerSwiped = {
            binding.beersList.setBeers(
                List(5) {
                    Beer(id = it, name = "Beer $it", tagLine = "Nice drink")
                }
            )
        }
    }

    private fun showActionPopUp(@DrawableRes icon: Int) = binding.imageViewAction.run {
        setImageResource(icon)
        visibility = View.VISIBLE
        startAnimationCompat(requireContext(), resId = pop_up, onEnd = {
            binding.imageViewAction.visibility = View.INVISIBLE
        })
    }
}