package com.baharudin.inews.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.baharudin.inews.R
import com.baharudin.inews.adapter.RekomendasiAdapter
import com.baharudin.inews.databinding.FragmentBookmarkBinding
import com.baharudin.inews.ui.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BookmarkFragment : Fragment(R.layout.fragment_bookmark) {

    private var _binding : FragmentBookmarkBinding? = null
    private val binding get() = _binding!!
    private val newsViewModel : NewsViewModel by activityViewModels()
    private lateinit var adapterNews : RekomendasiAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentBookmarkBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        setupRecycleview()
        subcribeNews()

        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = adapterNews.newsList[position]
                newsViewModel.deleteBookmark(article)
                Snackbar.make(view, "Succesfully delete Bookmark", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") {
                        newsViewModel.saveBookmark(article)
                    }
                    show()
                }
            }

        }
        ItemTouchHelper(itemTouchCallback).apply {
            attachToRecyclerView(binding.rvBookamrk)
        }

    }
    private fun setupRecycleview() {
        adapterNews = RekomendasiAdapter()
        adapterNews.setOnItemClickListener {
            val action = BookmarkFragmentDirections.actionBookmarkFragmentToDetailFragment(it)
            findNavController().navigate(action)
        }
        binding.rvBookamrk.apply {
            adapter = adapterNews
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
    private fun subcribeNews() = lifecycleScope.launch {
        newsViewModel.newsBookmark.collect {
            adapterNews.newsList = it
        }
    }
}