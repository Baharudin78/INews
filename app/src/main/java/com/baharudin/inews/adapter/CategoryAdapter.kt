package com.baharudin.inews.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.baharudin.inews.data.model.headline.Article
import com.baharudin.inews.databinding.ItemCategoryBinding
import com.bumptech.glide.Glide

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.CategoryHolder>() {

    lateinit var context : Context

    class CategoryHolder(val binding : ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    var diffUtil = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, diffUtil)
    var news : List<Article>
    get() = differ.currentList
    set(value) = differ.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val inflater = ItemCategoryBinding.inflate(
            LayoutInflater.from(
                parent.context
            ),parent,false
        )
        context = parent.context
        return CategoryHolder(inflater)
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        val newsCategory = news[position]
        holder.binding.apply {
            Glide.with(context)
                .load(newsCategory.urlToImage)
                .centerCrop()
                .into(image)
            tvJudul.text = newsCategory.title

            root.setOnClickListener {
                onItemClickListener?.invoke(newsCategory)
            }
        }

    }

    override fun getItemCount(): Int {
        return news.size
    }
    private var onItemClickListener : ((Article) -> Unit) ? = null
    fun setOnclickListener(listener : (Article) -> Unit) {
        onItemClickListener = listener
    }
}