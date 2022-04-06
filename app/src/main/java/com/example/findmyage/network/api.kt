package com.example.findmyage.network

import com.example.findmyage.models.News
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Query

interface api {


    @POST("LGLNews")
    suspend fun getResult(
        @Query("page")page: Int
    ): News

    companion object {
        private const val BASE_URL = "https://news.soolegal.com/api/index.php/"
        operator fun invoke(): api = Retrofit.Builder().baseUrl(BASE_URL).client(OkHttpClient.Builder().also {
            client -> val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC
            client.addInterceptor(logger)
        }.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api :: class.java)
    }
}