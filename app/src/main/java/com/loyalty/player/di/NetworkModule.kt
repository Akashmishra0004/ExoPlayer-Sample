package com.loyalty.player.di

import com.loyalty.player.BuildConfig
import com.loyalty.player.api.UserAPI
import com.loyalty.player.utils.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ConnectException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Suppress("UNREACHABLE_CODE")
@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }


    @Singleton
    @Provides
    fun providesUserAPI(retrofitBuilder: Retrofit.Builder): UserAPI {
        return try {
            val client = OkHttpClient.Builder()
            client.readTimeout(1600, TimeUnit.SECONDS)
            client.connectTimeout(1600, TimeUnit.SECONDS)

            client.addInterceptor(Interceptor { chain ->
                val newRequest: Request = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
//                    .addHeader("Authorization", "Bearer ${Constant.apiToken}")
                    .addHeader("appVersion", Constant.version)
                    .addHeader("platform", Constant.platform)
//                    .addHeader("Accept-Language", Constant.appLanguage)
//                    .addHeader("company", Constant.company)
//                    .addHeader("program", Constant.program)
                    .build()
                chain.proceed(newRequest)
            })


            val logging = HttpLoggingInterceptor()
            // if (BuildConfig.DEBUG) {
            if (BuildConfig.DEBUG) {
                logging.level = HttpLoggingInterceptor.Level.BODY
            } else {
                logging.level = HttpLoggingInterceptor.Level.NONE
            }

            client.addInterceptor(logging)

            return retrofitBuilder
                .client(client.build())
                .build()
                .create(UserAPI::class.java)
        }catch (e: Exception) {
            when (e) {
                is UnknownHostException -> {
                    throw RuntimeException(e)

                }
                is ConnectException -> {
                    throw RuntimeException(e)

                }
                else -> {
                    throw RuntimeException(e)
                }
            }

        }

    }

}