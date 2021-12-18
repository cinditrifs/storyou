package com.cindi.storyou.service


import com.google.gson.annotations.SerializedName

data class DataResponse2(
    @SerializedName("Data")
    var data: Data?,
    @SerializedName("Response")
    var response: String?,
    @SerializedName("Status")
    var status: String?
)