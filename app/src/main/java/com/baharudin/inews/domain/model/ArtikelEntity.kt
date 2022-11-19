package com.baharudin.inews.domain.model

import com.baharudin.inews.data.local.entity.Source

data class ArtikelEntity(
    var id: Int? = null,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
)
