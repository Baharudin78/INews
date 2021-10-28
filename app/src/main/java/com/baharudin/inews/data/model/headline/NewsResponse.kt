package com.baharudin.inews.data.model.headline

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)