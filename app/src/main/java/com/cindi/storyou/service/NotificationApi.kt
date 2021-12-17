package com.cindi.storyou.service

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface NotificationApi {
    // @Header ("Accept"  : "application/json", "Content-Type" : "application/json")
    @POST(value = "/send-message-topic")
    suspend fun pushNotification(
        @Body notification: PushNotification
    ): Response<ResponseBody>
}
//    companion object{
//        operator fun invoke(): NotificationApi {
//            val requestInterceptor= Interceptor{ chain ->
//                val url= chain.request()
//                    .url()
//                    .newBuilder()
//                    .build()
//                val request = chain.request()
//                    .newBuilder()
//                    .url(url)
//                    .build()
//
//                return@Interceptor chain.proceed((request))
//            }
//            val okHttpClient = OkHttpClient.Builder()
//                .addInterceptor(requestInterceptor)
//                .build()
//
//            return Retrofit.Builder()
//                .client(okHttpClient)
//                .baseUrl("https://storyouapp-1.herokuapp.com")
//                .addCallAdapterFactory(CoroutineCallAdapterFactory())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//                .create(NotificationApi::class.java)
//
//        }
//    }