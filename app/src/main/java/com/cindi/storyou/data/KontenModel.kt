package com.cindi.storyou.data

data class KontenModel (
    val kontenId : String?,
    val sampul : String,
    val judul : String,
    val pengarang : String,
    val konten : String
        ){
    constructor():this("", "", "","", "")
}
