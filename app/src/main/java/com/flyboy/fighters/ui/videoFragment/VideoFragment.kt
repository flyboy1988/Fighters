package com.flyboy.fighters.ui.videoFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flyboy.fighters.R
import com.flyboy.fighters.databinding.FragmentVideoBinding
import com.flyboy.fighters.model.MediaObject
import com.flyboy.fighters.utils.PlayerViewAdapter.Companion.playIndexThenPausePreviousPlayer
import com.flyboy.fighters.utils.RecyclerViewScrollListener
import com.flyboy.fighters.utils.autoCleared


class VideoFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    private var mAdapterVideos: FighterVideosRecyclerAdapter? = null
    private val modelList = ArrayList<MediaObject>()

    // for handle scroll and get first visible item index
    private lateinit var scrollListener: RecyclerViewScrollListener
    private var binding: FragmentVideoBinding by autoCleared()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVideoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findViews(view)
        setAdapter()
        // load data
        val model: MediaViewModel by viewModels()
        model.getMedia().observe(viewLifecycleOwner, Observer {
            Log.i("xxx", it.get(1).title.toString())
            mAdapterVideos?.updateList(arrayListOf(*it.toTypedArray()))
        })
    }

    private fun findViews(view: View) {
        recyclerView = view.findViewById(R.id.recycler_view) as RecyclerView
    }

    private fun setAdapter() {
        mAdapterVideos = FighterVideosRecyclerAdapter(requireActivity(), modelList)
        recyclerView!!.setHasFixedSize(true)

        // use a linear layout manager
        val layoutManager = LinearLayoutManager(activity)
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.adapter = mAdapterVideos
        scrollListener = object : RecyclerViewScrollListener() {
            override fun onItemIsFirstVisibleItem(index: Int) {
                Log.d("visible item index", index.toString())
                // play just visible item
                if (index != -1)
                    playIndexThenPausePreviousPlayer(index)
            }

        }
        recyclerView!!.addOnScrollListener(scrollListener)
        mAdapterVideos!!.SetOnItemClickListener(object : FighterVideosRecyclerAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, position: Int, model: MediaObject?) {

            }
        })
    }
}