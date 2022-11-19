package com.baharudin.inews.data.remote.api.dto

import com.baharudin.inews.data.remote.api.dto.ArticleDto

data class NewsResponse(
    val articles: List<ArticleDto>,
    val status: String,
    val totalResults: Int
)