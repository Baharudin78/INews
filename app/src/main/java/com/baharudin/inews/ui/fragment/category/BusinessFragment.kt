package com.baharudin.inews.ui.fragment.category

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.baharudin.inews.R
import com.baharudin.inews.adapter.CategoryAdapter
import com.baharudin.inews.databinding.FragmentBusinessBinding
import com.baharudin.inews.ui.viewmodel.NewsViewModel
import com.baharudin.inews.utils.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BusinessFragment  : Fragment(R.layout.fragment_business)  {

    private var _binding : FragmentBusinessBinding ?= null
    private val binding get() = _binding!!
    private val newsViewModel : NewsViewModel by activityViewModels()
    private lateinit var categortaAdapter : CategoryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentBusinessBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        setupRecycleview()

        getBusinessData()

    }
    private fun getBusinessData() {
        newsViewModel.getBusiness.observe(viewLifecycleOwner, {response ->
            when(response) {
                is Result.Sucess -> {
                    response.data?.let { result ->
                        setupRecycleview()
                        hideProgressbar()
                        categortaAdapter.differ.submitList(result.articles)
                    }
                }
                is Result.Error -> {
                    response.message?.let { message ->
                        hideProgressbar()
                        Log.e("error", response.message)
                    }
                }
                is Result.Loading -> {
                    showProgressbar()
                }
            }
        })
    }
    private fun setupRecycleview() {
        categortaAdapter = CategoryAdapter()
        binding.rvBisnis.apply {
            adapter = categortaAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }
    }
    private fun showProgressbar() {
        binding.progressBar2.visibility = View.VISIBLE
    }
    private fun hideProgressbar() {
        binding.progressBar2.visibility = View.GONE
    }
}