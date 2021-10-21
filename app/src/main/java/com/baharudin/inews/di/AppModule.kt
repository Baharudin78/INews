package com.baharudin.inews.di

import android.content.Context
import androidx.room.Room
import com.baharudin.inews.data.local.NewsDatabase
import com.baharudin.inews.data.local.dao.NewsDao
import com.baharudin.inews.data.remote.api.NewsApi
import com.baharudin.inews.repository.NewsRepo
import com.baharudin.inews.repository.NewsRepoImplementation
import com.baharudin.inews.utils.Constant.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNewsDatabase(
        @ApplicationContext context : Context
    ) : NewsDatabase = Room.databaseBuilder(
        context,
        NewsDatabase::class.java,
        "news_bookmark"
    ).build()

    @Provides
    @Singleton
    fun provideNewsDao(
        newsDatabase: NewsDatabase
    ) = newsDatabase.newsDao()

    @Provides
    @Singleton
    fun provideNewsApi() : NewsApi {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepo(
        newsApi: NewsApi,
        newsDao: NewsDao
    ) : NewsRepo {
        return NewsRepoImplementation(
            newsApi,
            newsDao
        )
    }
}