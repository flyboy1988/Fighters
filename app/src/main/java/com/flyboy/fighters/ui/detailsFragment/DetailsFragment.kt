package com.flyboy.fighters.ui.detailsFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.flyboy.fighters.R
import com.flyboy.fighters.databinding.FragmentDetailsBinding
import com.flyboy.fighters.model.Fighter
import com.flyboy.fighters.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import com.flyboy.fighters.model.Result
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_details.*

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private var detailsBinding: FragmentDetailsBinding by autoCleared()

    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        detailsBinding = FragmentDetailsBinding.inflate(inflater, container, false)
        return detailsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt("id")?.let {
            viewModel.getFighterDetail(it)
        }
        setUpObservers()
        setUpViews()
    }

    private fun setUpViews() {
        detailsBinding.btnVideos.setOnClickListener {
            findNavController().navigate(R.id.action_detailsFragment_to_videoFragment)
        }
    }

    private fun setUpObservers() {
        viewModel.movie.observe(viewLifecycleOwner, Observer { result ->
            when (result?.status) {
                Result.Status.SUCCESS -> {
                    result.data?.let {
                        updateUi(it)
                    }
                    loading.visibility = View.GONE
                }

                Result.Status.ERROR -> {
                    result.message?.let {
                        showError(it)
                    }
                    loading.visibility = View.GONE
                }

                Result.Status.LOADING -> {
                    loading.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun updateUi(fighter: Fighter) {

        detailsBinding.txtName.text = fighter.name.toString().toUpperCase()
        detailsBinding.txtNick.text = fighter.nickName.toString().toUpperCase()
        detailsBinding.txtRoll.text = fighter.role.toString().toUpperCase()
        txtDetails.text = fighter.description.toString().toUpperCase()
        Glide.with(this).load(fighter.poster_path)
            .apply(
                RequestOptions().override(400, 400).centerInside()
                    .placeholder(R.drawable.placehoder)
            ).into(ivDetailFighter)

    }

    private fun showError(msg: String) {
        Log.i("main", msg)
        Snackbar.make(detailsBinding.detailsRoot, msg, Snackbar.LENGTH_INDEFINITE)
            .setAction("DISMISS") {
            }.show()
    }

}