package com.flyboy.fighters.ui.mainFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.flyboy.fighters.R
import com.flyboy.fighters.databinding.FragmentMainBinding
import com.flyboy.fighters.model.Fighter
import com.flyboy.fighters.model.Fighters
import com.flyboy.fighters.model.Result
import com.flyboy.fighters.utils.ConnectionLiveData
import com.flyboy.fighters.utils.autoCleared
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import de.taop.swipeaction.AlternatingBackgroundItemDecoration
import de.taop.swipeaction.SwipeActionSetupHelper
import de.taop.swipeaction.actions.DeleteAction
import de.taop.swipeaction.actions.SwipeAction
import kotlinx.android.synthetic.main.fragment_main.*

@AndroidEntryPoint
class MainFragment : Fragment() {
    private var binding: FragmentMainBinding by autoCleared()
    lateinit var adapter2: FighterSwipeAdapter
    private val viewModel: FightersViewModel by viewModels()
    private var oldItemTouchHelper: ItemTouchHelper? = null
    val myArray: Fighters = Fighters()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }

    private fun setupRecyclerView2(list: List<Fighter>) {

        myArray.clear()
        myArray?.addAll(list)
        //setup recyclerview
        rvFighters2.layoutManager = LinearLayoutManager(requireActivity())
        rvFighters2.adapter = FighterSwipeAdapter(myArray)

        val pendingDeleteAction = DeleteAction(rvFighters2, parent_layout)
        val instantDeleteAction = DeleteAction(rvFighters2, parent_layout,
            false,
            iconID = R.drawable.ic_delete_forever_black_24dp,
            colorID = R.color.colorInstantDelete,
            name = "instantDelete")

        // set delete snackbar Text
        pendingDeleteAction.setSnackBarText(R.string.deleteUndo, R.plurals.deletedItems)

        // on delete listener
        instantDeleteAction.itemsDeletedCallback = {
        }

        pendingDeleteAction.itemsDeletedCallback = {
        }

        // set alternating background colors (optional)
        rvFighters2.addItemDecoration(AlternatingBackgroundItemDecoration(requireActivity(), R.color.pink1_trans, R.color.blue1_trans))

        /*initialize the swipe actions
        this is required! it's not used in the example because we use the two spinners to set up the recyclerview*/
        // SwipeActionSetupHelper.setUpRecyclerView(this@MainActivity, list, pendingDeleteAction, pendingDeleteAction)

        // apply different actions
        var swipeLeftAction: SwipeAction = pendingDeleteAction
        var swipeRightAction: SwipeAction = pendingDeleteAction
        updateRecyclerView(swipeLeftAction, swipeRightAction)

    }


    private fun setupObservers() {
        viewModel.fighterList.observe(viewLifecycleOwner, Observer { result ->
            when (result?.status) {
                Result.Status.SUCCESS -> {
                    result.data?.results?.let { list ->
                        setupRecyclerView2(list)
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
        Snackbar.make(binding.parentLayout, msg, Snackbar.LENGTH_INDEFINITE).setAction("DISMISS") {
        }.show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }
    private fun updateRecyclerView(
        swipeLeftAction: SwipeAction,
        swipeRightAction: SwipeAction
    ) {
        // if an pending delete is active --> undo it
        (swipeLeftAction as? DeleteAction)?.undoPendingItems(true)
        (swipeRightAction as? DeleteAction)?.undoPendingItems(true)
        // reset all items --> redraws the item!
       rvFighters2.adapter?.notifyItemRangeChanged(
            0,
            rvFighters2.adapter!!.itemCount - 1
        )
        // This is absolutely necessary if you want to change the action on the fly!
        // Otherwise the actions won't update!
        oldItemTouchHelper?.attachToRecyclerView(null)
        oldItemTouchHelper = SwipeActionSetupHelper.setUpRecyclerView(
            requireActivity(),
            binding.rvFighters2,
            swipeLeftAction,
            swipeRightAction
        )

    }

}