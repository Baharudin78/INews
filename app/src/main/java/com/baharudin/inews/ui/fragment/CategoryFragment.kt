package com.baharudin.inews.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.baharudin.inews.R
import com.baharudin.inews.databinding.FragmentCategoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : Fragment(R.layout.fragment_category)  {

    private var _binding : FragmentCategoryBinding ? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentCategoryBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        binding.btTechnology.setOnClickListener {
            findNavController().navigate(R.id.action_categoryFragment_to_technologyFragment)
        }
        binding.btSport.setOnClickListener {
            findNavController().navigate(R.id.action_categoryFragment_to_sportFragment)
        }
        binding.btScience.setOnClickListener {
            findNavController().navigate(R.id.action_categoryFragment_to_scienceFragment)
        }
        binding.btHiburan.setOnClickListener {
            findNavController().navigate(R.id.action_categoryFragment_to_entertainmentFragment)
        }
        binding.btHealth.setOnClickListener {
            findNavController().navigate(R.id.action_categoryFragment_to_healthFragment)
        }
        binding.btBisnis.setOnClickListener {
            findNavController().navigate(R.id.action_categoryFragment_to_businessFragment)
        }
    }


}