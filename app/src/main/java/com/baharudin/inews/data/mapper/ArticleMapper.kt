package com.baharudin.inews.data.mapper

import com.baharudin.inews.data.remote.api.dto.ArticleDto
import com.baharudin.inews.data.remote.api.dto.SourceDto
import com.baharudin.inews.domain.model.ArtikelEntity
import com.baharudin.inews.domain.model.SourceEntity

fun ArtikelEntity.toArticleDto() : ArticleDto {
    return ArticleDto(
        id = id,
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = source,
        title = title,
        url = url,
        urlToImage = urlToImage
    )
}

fun ArticleDto.toArtikelEntity(): ArtikelEntity{
    return ArtikelEntity(
        id = id,
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = source,
        title = title,
        url = url,
        urlToImage = urlToImage
    )
}

fun SourceEntity.toSourceDto() : SourceDto {
    return SourceDto(
        id = id,
        name = name
    )
}

fun SourceDto.toSourceEntity() : SourceEntity {
    return SourceEntity(
        id = id,
        name = name
    )
}