package com.baharudin.inews.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.baharudin.inews.data.model.headline.Article
import com.baharudin.inews.databinding.ItemHeadlineBinding
import com.bumptech.glide.Glide

class HeadlineAdapter: RecyclerView.Adapter<HeadlineAdapter.HeadlineHolder>() {
    lateinit var context : Context

    class HeadlineHolder(val binding : ItemHeadlineBinding) : RecyclerView.ViewHolder(binding.root)

    val diffUtil = object :DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }
    var differ = AsyncListDiffer(this, diffUtil)
    var newsList : List<Article>
    get() = differ.currentList
    set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadlineHolder {
        val inflaterView = ItemHeadlineBinding.inflate(
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
                .into(ivToprated)
            tvJudul.text = news.title
            root.setOnClickListener {
                onItemClickListener?.invoke(news)
            }
        }
    }

    override fun getItemCount(): Int {
        return newsList.size
    }
    private var onItemClickListener : ((Article) -> Unit) ? = null
    fun setOnItemClickListener(listener : (Article) -> Unit) {
        onItemClickListener = listener
    }
}