package com.baharudin.inews.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.baharudin.inews.R
import com.baharudin.inews.adapter.HeadlineAdapter
import com.baharudin.inews.adapter.RekomendasiAdapter
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
    private lateinit var adapterRekomendasi : RekomendasiAdapter
    private val newsViewModel : NewsViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentHomeBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        setupRecycleview()
        setupRecycleviewRekomendasi()

        getHeadline()
        getRekomendasi()

        binding.searchBar.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }


    }
    private fun getHeadline() {
        newsViewModel.topHeadlines.observe(viewLifecycleOwner, { response ->
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
    private fun getRekomendasi() {
        newsViewModel.sourceNews.observe(viewLifecycleOwner, { response ->
            when(response) {
                is Result.Sucess -> {
                    response.data?.let { result ->
                        hideProgress()
                        setupRecycleviewRekomendasi()
                        adapterRekomendasi.differ.submitList(result.articles)
                    }
                }
                is Result.Error -> {
                    hideProgress()
                    response.message?.let { message ->
                        Log.e("error rekomedasi", response.message)
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
        adapterNews.setOnItemClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(it)
            findNavController().navigate(action)
        }
        binding.rvHeadline.apply {
            adapter = adapterNews
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }
    private fun setupRecycleviewRekomendasi() {
        adapterRekomendasi = RekomendasiAdapter()
        adapterRekomendasi.setOnItemClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(it)
            findNavController().navigate(action)
        }
        binding.rvRekomendasi.apply {
            adapter = adapterRekomendasi
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
    private fun showProgress() {
        binding.progressBar.visibility = View.VISIBLE
    }
    private fun hideProgress() {
        binding.progressBar.visibility = View.GONE
    }
}