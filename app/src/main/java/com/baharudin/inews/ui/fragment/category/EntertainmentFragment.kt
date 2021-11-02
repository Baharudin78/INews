package com.baharudin.inews.ui.fragment.category

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.baharudin.inews.R
import com.baharudin.inews.adapter.CategoryAdapter
import com.baharudin.inews.databinding.FragmentEntertainmentBinding
import com.baharudin.inews.ui.viewmodel.NewsViewModel
import com.baharudin.inews.utils.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EntertainmentFragment  : Fragment(R.layout.fragment_entertainment)  {

    private var _binding : FragmentEntertainmentBinding? = null
    private val binding get() = _binding!!
    private val newsViewModel : NewsViewModel by activityViewModels()
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentEntertainmentBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        setupRecycleview()
        getEntertainmentData()
    }
    private fun getEntertainmentData() {
        newsViewModel.getEntertainmentCategory.observe(viewLifecycleOwner, {response ->
            when(response) {
                is Result.Sucess -> {
                    response.data?.let { result ->
                        hideProgressbar()
                        setupRecycleview()
                        categoryAdapter.differ.submitList(result.articles)
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
        categoryAdapter = CategoryAdapter()
        categoryAdapter.setOnclickListener {
            val action = EntertainmentFragmentDirections.actionEntertainmentFragmentToDetailFragment(it)
            findNavController().navigate(action)
        }
        binding.rvEntertainment.apply{
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