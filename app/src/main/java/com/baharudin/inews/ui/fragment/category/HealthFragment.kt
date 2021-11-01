package com.baharudin.inews.ui.fragment.category

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.baharudin.inews.R
import com.baharudin.inews.adapter.CategoryAdapter
import com.baharudin.inews.databinding.FragmentHealthBinding
import com.baharudin.inews.ui.viewmodel.NewsViewModel
import com.baharudin.inews.utils.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HealthFragment : Fragment(R.layout.fragment_health) {

    private var _binding : FragmentHealthBinding? = null
    private val binding get() = _binding!!
    private val newsViewModel : NewsViewModel by activityViewModels()
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentHealthBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        setupRecycleview()
        getHealthData()
    }
    private fun getHealthData() {
        newsViewModel.getHealthCategory.observe(viewLifecycleOwner, {response ->
            when(response) {
                is Result.Sucess -> {
                    response.data?.let { result ->
                        hideProgressbar()
                        setupRecycleview()
                        categoryAdapter.differ.submitList(result.articles)
                    }
                }
                is Result.Error -> {
                    response.message?.let{
                        hideProgressbar()
                        Log.e("Error", response.message)
                    }
                }
                is Result.Loading -> {
                    showProgressbar()
                }
            }
        })
    }

    private fun setupRecycleview() {
        categoryAdapter = CategoryAdapter()
        binding.rvHealth.apply {
            adapter = CategoryAdapter()
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