package com.flyboy.fighters.ui.mainFragment

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.flyboy.fighters.R
import com.flyboy.fighters.model.Fighter
import de.taop.swipeaction.actions.SwipeActionAdapter
import kotlinx.android.synthetic.main.item_fighter.view.*


class FighterSwipeAdapter(private var items: ArrayList<Fighter>, private val itemClick: (Fighter) -> Unit = {})
    : RecyclerView.Adapter<FighterSwipeAdapter.Fighter2ViewHolder>(),
    SwipeActionAdapter {

    fun setItems(items: List<Fighter>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun addItem(item: Any) {
        items.add(item as Fighter)
        notifyItemInserted(items.size - 1)
    }

    override fun addItemAt(position: Int, item: Any) {
        items.add(position, item as Fighter)
        notifyItemInserted(position)
    }

    override fun removeItemAt(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemAt(position: Int): Fighter = items[position]

    override fun containsItem(item: Any): Boolean = items.contains(item)

    override fun getIndexOfItem(itemAt: Any): Int = items.indexOf(itemAt)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Fighter2ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_fighter, parent, false)
        return Fighter2ViewHolder(view, itemClick)
    }

//    fun setData(newDataSet: MutableList<Fighter>) {
//        items = newDataSet.toList() as ArrayList<Fighter>
//        notifyDataSetChanged()
//    }

    override fun onBindViewHolder(holder: Fighter2ViewHolder, position: Int) {
        holder.bindView(items[position], position)
    }

    override fun getItemCount(): Int = items.size

    class Fighter2ViewHolder(val view: View, val itemClick: (Fighter) -> Unit) : RecyclerView.ViewHolder(view) {

        fun bindView(item: Fighter, position: Int) {
            with(item) {
                itemView.name .text = item.name
                itemView.nickName .text = item.nickName
                itemView.setOnClickListener { itemClick(this)
                    itemView .findNavController().navigate(
                        R.id.action_mainFragment_to_detailsFragment,
                        bundleOf("id" to item.id)
                    )
                }
                Glide.with(itemView.image)
                    .load(item.poster_path)
                    .transform(CircleCrop())
                    .into(itemView.image)
            }


        }
    }

}