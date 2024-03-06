package io.ilyasin.logonex.data.network

import io.ilyasin.logonex.BuildConfig
import io.ilyasin.logonex.data.network.schemas.ServerResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("products/")
    suspend fun products(@Query("limit") limit: Int): ServerResponse

    companion object {
        private val interceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        private val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        fun create(): ApiService = retrofit.create(ApiService::class.java)
    }
}