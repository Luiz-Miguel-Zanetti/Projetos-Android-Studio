package com.example.marvelappstarter.di

import com.example.marvelappstarter.data.remote.ServiceApi
import com.example.marvelappstarter.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.InstallIn
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import timber.log.Timber
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import javax.inject.Inject
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Singleton
    @Provides
    fun provideOkHttpClient() : OkHttpClient{

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient().newBuilder().addInterceptor{

            chain ->

            val currentTimestamp = System.currentTimeMillis()
            val newUrl = chain.request().url.newBuilder()
                .addQueryParameter(Constants.TS, currentTimestamp.toString())
                .addQueryParameter(Constants.API_KEY, Constants.PUBLIC_KEY)
                .addQueryParameter(Constants.HASH, provideToMd5Hash(currentTimestamp.toString() + Constants.PRIVATE_KEY +
                Constants.PUBLIC_KEY)).build()

            val newRequest = chain.request().newBuilder()
                .url(newUrl).build()
            chain.proceed(newRequest)
        }

            .addInterceptor(logging)
            .build()





    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient) : Retrofit{
        return Retrofit.Builder().
        baseUrl(Constants.BASE_URL).
        addConverterFactory(GsonConverterFactory.create()).client(client).build()

    }

    @Singleton
    @Provides
    fun provideServiceApi(retrofit: Retrofit) : ServiceApi{

        return retrofit.create(ServiceApi::class.java)

    }

    fun provideToMd5Hash(encrypted: String): String {
        var pass = encrypted
        var encryptedString: String? = null
        val md5: MessageDigest
        try {
            md5 = MessageDigest.getInstance("MD5")
            md5.update(pass.toByteArray(), 0, pass.length)
            pass = BigInteger(1, md5.digest()).toString(16)
            while (pass.length < 32) {
                pass = "0$pass"
            }
            encryptedString = pass
        } catch (e1: NoSuchAlgorithmException) {
            e1.printStackTrace()
        }
        Timber.d("hash -> $encryptedString")
        return encryptedString ?: ""
    }



}