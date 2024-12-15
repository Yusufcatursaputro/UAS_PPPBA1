package com.example.uas.model

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("_id")
    val id: String,
    @SerializedName("judul")
    val judul: String,
    @SerializedName("episode")
    val episode: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("genre")
    val genre: String,
    @SerializedName("sinopsis")
    val sinopsis: String
)
