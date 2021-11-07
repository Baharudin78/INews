package com.baharudin.inews.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.baharudin.inews.R
import com.baharudin.inews.adapter.RekomendasiAdapter
import com.baharudin.inews.databinding.FragmentSearchBinding
import com.baharudin.inews.ui.viewmodel.NewsViewModel
import com.baharudin.inews.utils.Constant.SEARCH_NEWS_TIME_DELAY
import com.baharudin.inews.utils.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private var _binding : FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val newsViewModel : NewsViewModel by activityViewModels()
    private lateinit var searchAdapter : RekomendasiAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentSearchBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        setupRecycleview()
        getSearchData()

        var job : Job? = null
        binding.searchBar.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_NEWS_TIME_DELAY)
                editable?.let {
                    if (editable.toString().isNotEmpty()) {
                        newsViewModel.getSearchNews(editable.toString())
                    }
                }
            }
        }
    }
    private fun getSearchData() {
        newsViewModel.searchNews.observe(viewLifecycleOwner, {response ->
            when(response) {
                is Result.Sucess -> {
                    hideProgressbar()
                    response.data?.let { result ->
                        searchAdapter.differ.submitList(result.articles)
                    }
                }
                is Result.Error -> {
                    hideProgressbar()
                    response.message?.let { message ->
                        Log.e("error", message)
                    }
                }
                is Result.Loading -> {
                    showProgressbar()
                }
            }
        })
    }
    private fun setupRecycleview() {
        searchAdapter = RekomendasiAdapter()
        searchAdapter.setOnItemClickListener {
            val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(it)
            findNavController().navigate(action)
        }
        binding.rvSearching.apply {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun showProgressbar() {
        binding.progressBar.visibility = View.VISIBLE
    }
    private fun hideProgressbar() {
        binding.progressBar.visibility = View.GONE
    }
}