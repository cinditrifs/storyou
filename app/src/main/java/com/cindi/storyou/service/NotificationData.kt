package com.cindi.storyou.service

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NotificationData(
    @SerializedName("message")
    @Expose
    val message: String,
    @SerializedName("nama")
    @Expose
    val nama: String,
    @SerializedName("title")
    @Expose
    val title: String,
    @SerializedName("topic")
    @Expose
    val topic: String
)