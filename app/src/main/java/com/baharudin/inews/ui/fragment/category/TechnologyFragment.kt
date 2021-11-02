package com.baharudin.inews.ui.fragment.category

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.baharudin.inews.R
import com.baharudin.inews.adapter.CategoryAdapter
import com.baharudin.inews.databinding.FragmentTechnologyBinding
import com.baharudin.inews.ui.viewmodel.NewsViewModel
import com.baharudin.inews.utils.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TechnologyFragment  : Fragment(R.layout.fragment_technology)  {

    private var _binding : FragmentTechnologyBinding? = null
    private val binding get() = _binding!!
    private val newsViewModel : NewsViewModel by activityViewModels()
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentTechnologyBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        setupRecycleview()
        getTechData()
    }

    private fun getTechData() {
        newsViewModel.getTechCategory.observe(viewLifecycleOwner, { response ->
            when(response) {
                is Result.Sucess -> {
                    response.data?.let { result ->
                        setupRecycleview()
                        hideProgressbar()
                        categoryAdapter.differ.submitList(result.articles)
                    }
                }
                is Result.Error -> {
                    response.message?.let {
                        hideProgressbar()
                        Log.e("errorTech", response.message)
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
        categoryAdapter.setOnclickListener {
            val action = TechnologyFragmentDirections.actionTechnologyFragmentToDetailFragment(it)
            findNavController().navigate(action)
        }
        binding.rvTech.apply {
            adapter = categoryAdapter
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