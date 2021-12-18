package com.cindi.storyou.service


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DataResponse(
//    @SerializedName("Data")
//    var `data`: DataRequest?,
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
    @SerializedName("Response")
    @Expose
    var response: String?=null,
    @SerializedName("Status")
    @Expose
    var status: String?= null
)