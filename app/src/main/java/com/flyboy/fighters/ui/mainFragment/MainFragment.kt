package com.flyboy.fighters.ui.mainFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.flyboy.fighters.R
import com.flyboy.fighters.databinding.FragmentMainBinding
import com.flyboy.fighters.model.Result
import com.flyboy.fighters.utils.autoCleared
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(), FightersAdapter.FighterListener {
    private var binding: FragmentMainBinding by autoCleared()
    lateinit var adapter: FightersAdapter
    private val viewModel: FightersViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()

    }

    private fun setupObservers() {
        viewModel.fighterList.observe(viewLifecycleOwner, Observer { result ->

            when (result?.status) {
                Result.Status.SUCCESS -> {
                    result.data?.results?.let { list ->
                        adapter.setItems(list)
                        // moviesAdapter.updateData(list)
                    }
                    binding.loading.visibility = View.GONE
                }

                Result.Status.ERROR -> {
                    result.error?.let {
                        showError(it)
                        binding.loading.visibility = View.GONE

                    }
                }

                Result.Status.LOADING -> {
                    binding.loading.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun showError(msg: String) {
        Log.i("main", msg)
        Snackbar.make(binding.parent, msg, Snackbar.LENGTH_INDEFINITE).setAction("DISMISS") {
        }.show()
    }

    private fun setupRecyclerView() {
        adapter = FightersAdapter(this)
        binding.rvFighters.layoutManager = LinearLayoutManager(requireContext())
       // binding.rvFighters.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvFighters.adapter = adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onClickedFighter(characterId: Int) {
        findNavController().navigate(
            R.id.action_mainFragment_to_detailsFragment,
            bundleOf("id" to characterId)
        )
    }


}