package com.rashwan.accunews.di.module

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.rashwan.accunews.R
import com.rashwan.accunews.data.NewsAPIs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        httpClient.connectTimeout(60, TimeUnit.SECONDS)
        httpClient.readTimeout(60, TimeUnit.SECONDS)
        return httpClient.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(context: Context, gson: Gson?, okHttpClient: OkHttpClient?): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson!!))
            .baseUrl(context.getString(R.string.base_url))
            .client(okHttpClient!!)
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsAPIs(retrofit: Retrofit): NewsAPIs {
        return retrofit.create(NewsAPIs::class.java)
    }
}
