package com.baharudin.inews.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.baharudin.inews.R
import com.baharudin.inews.adapter.RekomendasiAdapter
import com.baharudin.inews.databinding.FragmentBreakingNewsBinding
import com.baharudin.inews.ui.viewmodel.NewsViewModel
import com.baharudin.inews.utils.Result
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {

    private var _binding : FragmentBreakingNewsBinding? =null
    private val binding get() = _binding!!
    private lateinit var newsAdapter : RekomendasiAdapter
    private val newsViewModel : NewsViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentBreakingNewsBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        setupRecycleview()
        getData()
    }
    private fun getData() {
        newsViewModel.topHeadlines.observe(viewLifecycleOwner, {response ->
            when(response) {
                is Result.Sucess -> {
                    hideProgressbar()
                    response.data?.let { result ->
                        setupRecycleview()
                        newsAdapter.differ.submitList(result.articles)
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
        newsAdapter = RekomendasiAdapter()
        newsAdapter.setOnItemClickListener {
            val action = BreakingNewsFragmentDirections.actionBreakingNewsFragmentToDetailFragment(it)
            findNavController().navigate(action)
        }
        binding.rvHeadline.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
    private fun hideProgressbar() {
        binding.progresBar.visibility = View.GONE
    }
    private fun showProgressbar() {
        binding.progresBar.visibility = View.VISIBLE
    }
}