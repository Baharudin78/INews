package com.baharudin.inews.domain.repository

import androidx.room.Query
import com.baharudin.inews.domain.model.ArtikelEntity
import com.baharudin.inews.utils.Result
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getAllArticle(
        fetchFromRemote : Boolean,
        query: String
    ) : Flow<Result<List<ArtikelEntity>>>


}