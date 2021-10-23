package com.baharudin.inews.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.baharudin.inews.R
import com.baharudin.inews.adapter.HeadlineAdapter
import com.baharudin.inews.databinding.FragmentHomeBinding
import com.baharudin.inews.ui.viewmodel.NewsViewModel
import com.baharudin.inews.utils.Result
import com.bumptech.glide.load.engine.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home)  {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapterNews : HeadlineAdapter
    private val newsViewModel : NewsViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentHomeBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        setupRecycleview()

        newsViewModel.topHeadlines.observe(viewLifecycleOwner, Observer {response ->
            when(response) {
                is Result.Sucess -> {
                    response.data?.let {newsResponse ->
                        hideProgress()
                        setupRecycleview()
                        adapterNews.differ.submitList(newsResponse.articles)
                    }
                }
                is Result.Error -> {
                    hideProgress()
                    response.message?.let { messege ->
                        Log.e("error", "di $messege")
                    }
                }
                is Result.Loading -> {
                    showProgress()
                }
            }
        })
    }
    private fun setupRecycleview() {
        adapterNews = HeadlineAdapter()
        binding.rvHeadline.apply {
            adapter = adapterNews
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }
    private fun showProgress() {
        binding.progressBar.visibility = View.VISIBLE
    }
    private fun hideProgress() {
        binding.progressBar.visibility = View.GONE
    }
}