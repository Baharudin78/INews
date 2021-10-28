package com.baharudin.inews.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.baharudin.inews.data.model.headline.Article
import com.baharudin.inews.data.local.converter.Converter
import com.baharudin.inews.data.local.dao.NewsDao

@Database(
    entities = [Article::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converter::class)
abstract class NewsDatabase : RoomDatabase(){
    abstract fun newsDao() : NewsDao
}