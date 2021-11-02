package com.baharudin.inews.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.baharudin.inews.R
import com.baharudin.inews.databinding.FragmentDetailBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private var _binding : FragmentDetailBinding? = null
    private val binding get() = _binding!!
    val args by navArgs<DetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentDetailBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        getData()
    }
    private fun getData() {
        val news = args.detailNews
        binding.apply {
            Glide.with(this@DetailFragment)
                .load(news.urlToImage)
                .into(ivPoster)
            tvJudul.text = news.title
            tvPublishAt.text = news.publishedAt
            tvAuthor.text = news.author
            tvSumber.text = news.source.name
            tvDesc.text = news.description
        }

    }
}