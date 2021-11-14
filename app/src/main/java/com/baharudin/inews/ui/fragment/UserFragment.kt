package com.baharudin.inews.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.baharudin.inews.R
import com.baharudin.inews.databinding.FragmentUserBinding
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import androidx.fragment.app.FragmentManager
import com.baharudin.inews.ui.activity.LoginActivity


@AndroidEntryPoint
class UserFragment : Fragment(R.layout.fragment_user) {

    private var _binding : FragmentUserBinding ?= null
    private val binding get() = _binding!!
    private lateinit var mAuth : FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentUserBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser

        binding.tvNama.text = currentUser?.displayName
        binding.tvEmail.text = currentUser?.email
        Glide.with(requireActivity())
            .load(currentUser?.photoUrl)
            .into(binding.profileImage)

        binding.btLogout.setOnClickListener {
            mAuth.signOut()
            val intentBack = activity?.intent
            intentBack.run {
                val intent = Intent(requireActivity(), LoginActivity::class.java)
                startActivity(intent)
            }
            activity?.finish()
        }
    }
}