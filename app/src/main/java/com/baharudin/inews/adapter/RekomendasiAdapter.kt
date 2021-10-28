package com.baharudin.inews.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.baharudin.inews.data.model.headline.Article
import com.baharudin.inews.databinding.ItemHeadlineBinding
import com.baharudin.inews.databinding.ItemRekomendasiBinding
import com.bumptech.glide.Glide

class RekomendasiAdapter : RecyclerView.Adapter<RekomendasiAdapter.HeadlineHolder>() {
    lateinit var context : Context

    class HeadlineHolder(val binding : ItemRekomendasiBinding) : RecyclerView.ViewHolder(binding.root)

    val diffUtil = object :DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, diffUtil)
    var newsList : List<Article>
    get() = differ.currentList
    set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadlineHolder {
        val inflaterView = ItemRekomendasiBinding.inflate(
            LayoutInflater.from(
                parent.context
            ),
            parent, false
        )
        context = parent.context
        return HeadlineHolder(inflaterView)
    }

    override fun onBindViewHolder(holder: HeadlineHolder, position: Int) {
        val news = newsList[position]
        holder.binding.apply {
            Glide.with(context)
                .load(news.urlToImage)
                .centerCrop()
                .into(image)
            tvJudulBerita.text = news.title
            tvdescription.text = news.description
        }
    }

    override fun getItemCount(): Int {
        return newsList.size
    }
}