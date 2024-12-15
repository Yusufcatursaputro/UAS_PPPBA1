package com.example.uas.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "note_anime")
data class Note(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "judul")
    val judul: String,
    @ColumnInfo(name = "episode")
    val episode: String,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "genre")
    val genre: String,
    @ColumnInfo(name = "sinopsis")
    val sinopsis: String
)
