package com.flyboy.fighters.ui.mainFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.flyboy.fighters.R
import com.flyboy.fighters.databinding.ItemFighterBinding
import com.flyboy.fighters.model.Fighter

class FightersAdapter(private val listener: FighterListener) :
    RecyclerView.Adapter<FightersAdapter.FightersViewHolder>() {
    interface FighterListener {
        fun onClickedFighter(characterId: Int)
    }

    private val items = ArrayList<Fighter>()

    fun setItems(items: List<Fighter>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FightersViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding  : ItemFighterBinding=DataBindingUtil. inflate(inflater,
            R.layout.item_fighter,parent,false)
        return FightersViewHolder(binding,listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: FightersViewHolder, position: Int) {
holder.itemBinding.fighter=items[position]
        holder.bind(items[position])
    }


   inner class FightersViewHolder(
        val itemBinding: ItemFighterBinding,
        val listener: FightersAdapter.FighterListener
    ) : RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {
        private lateinit var fighter: Fighter

        init {
            itemBinding.root.setOnClickListener(this)
        }

        fun bind(item: Fighter) {
            this.fighter = item
        }

        override fun onClick(v: View?) {
            listener.onClickedFighter(fighter.id)
        }
    }
}


