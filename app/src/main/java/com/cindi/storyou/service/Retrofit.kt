package com.cindi.storyou.service

import com.cindi.storyou.service.Constants.Companion.BASE_URL
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class Retrofit {

//    companion object{
//        private val retrofit by lazy {
//            Retrofit.Builder()
//                .baseUrl("https://storyouapp-1.herokuapp.com")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//        }
//
//        val api by lazy {
//            retrofit.create(NotificationApi::class.java)
//        }
//    }


    fun getInstance (url :String ):Retrofit{
        val gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }
}