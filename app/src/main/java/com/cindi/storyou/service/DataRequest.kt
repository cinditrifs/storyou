package com.cindi.storyou.service


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DataRequest(
    @SerializedName("message")
    @Expose
    var message : String,
    @SerializedName("nama")
    @Expose
    var nama: String,
    @SerializedName("title")
    @Expose
    var title: String,
    @SerializedName("topic")
    @Expose
    var topic: String,
)