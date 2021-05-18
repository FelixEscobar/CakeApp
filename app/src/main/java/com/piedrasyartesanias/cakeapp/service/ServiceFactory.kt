package com.piedrasyartesanias.cakeapp.service

import android.content.Context
import com.google.gson.GsonBuilder
import com.piedrasyartesanias.cakeapp.utils.ConnectionInterceptor
import com.piedrasyartesanias.cakeapp.utils.URLBASE
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ServiceFactory {

    private var restAdapter:Retrofit? = null

    fun serviceImplementation(context: Context){
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(ConnectionInterceptor(context))
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS )
            .readTimeout(10, TimeUnit.SECONDS)
            .build()
        restAdapter = Retrofit.Builder()
            .baseUrl(URLBASE)
            .client(okHttpClient)
            .addConverterFactory(getGsonConverter())
            .build()
    }

    private fun getGsonConverter(): Converter.Factory {
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd").create()
        return GsonConverterFactory.create(gson)
    }

    fun getInstanceCakeService(context: Context): CakeService{
        serviceImplementation(context)
        return restAdapter!!.create(CakeService::class.java)
    }
}