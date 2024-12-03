package com.example.buscadorpeliculas_app.utils

import com.example.buscadorpeliculas_app.retrofit.FilmsService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitProvider {
    companion object
    {
        fun makeRetrofitService():FilmsService
        {
            val retrofit= Retrofit.Builder()
                .baseUrl("https://www.omdbapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(FilmsService::class.java)
        }
    }
}