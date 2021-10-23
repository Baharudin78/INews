package com.baharudin.inews.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.baharudin.inews.R
import com.baharudin.inews.databinding.FragmentHomeBinding
import com.baharudin.inews.ui.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home)  {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val newsViewModel : NewsViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentHomeBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
    }
}