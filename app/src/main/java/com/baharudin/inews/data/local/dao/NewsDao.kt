package com.baharudin.inews.data.local.dao

import androidx.room.*
import com.baharudin.inews.data.model.headline.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(article: Article)

    @Query("SELECT * FROM articles")
    fun getAllBookmark() : Flow<List<Article>>

    @Delete
    suspend fun deleteBookmark(article: Article)
}