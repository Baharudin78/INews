package com.baharudin.inews.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.baharudin.inews.R
import com.baharudin.inews.databinding.FragmentDetailBinding
import com.baharudin.inews.ui.viewmodel.NewsViewModel
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private var _binding : FragmentDetailBinding? = null
    private val binding get() = _binding!!
    val args by navArgs<DetailFragmentArgs>()
    private val newsViewModel : NewsViewModel by activityViewModels()

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
            tvDesc.text = news.content
            shareLink.setOnClickListener {
                val shareSheet = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, news.url)
                    setType("text/plain")
                }
                startActivity(Intent.createChooser(shareSheet,"Send via"))
            }
        }
        saveNews()
        subcribeBookmark()

    }
    private fun saveNews() {
        binding.apply {
            btSave.setOnClickListener {
                val newsArgs = args.detailNews
                try {
                    newsViewModel.saveBookmark(newsArgs)
                    btSave.setImageResource(R.drawable.ic_bookmarked)

                    Snackbar.make(it, "Article saved successfully", Snackbar.LENGTH_SHORT).show()
                }catch (e : Exception) {
                    e.message?.let { it1 -> Log.e("roomError", it1) }
                }
            }
        }
    }
    private fun subcribeBookmark() {
        val newsArgs = args.detailNews
        if (newsArgs.id != null) {
            binding.btSave.isClickable = false
            binding.btSave.setImageResource(R.drawable.ic_bookmarked)
        }
    }
}