package com.baharudin.inews.data.local.dao

import androidx.room.*
import com.baharudin.inews.data.local.entity.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(article: Article)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllArticle(
        article: List<Article>
    )

    @Query("""
        SELECT * FROM articles
        WHERE LOWER(title)
    """)
    suspend fun searchArticle(query : String) : List<Article>


    @Query("SELECT * FROM articles")
    fun getAllBookmark() : Flow<List<Article>>

    @Delete
    suspend fun deleteBookmark(article: Article)
}