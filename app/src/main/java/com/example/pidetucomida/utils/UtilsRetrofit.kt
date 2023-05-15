package com.example.pidetucomida.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UtilsRetrofit {

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("http://192.168.0.19:8080/PideTuComida/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
}