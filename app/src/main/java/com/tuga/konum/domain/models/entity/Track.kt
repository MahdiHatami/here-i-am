package com.tuga.konum.domain.models.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "track")
data class Track(
  @PrimaryKey(autoGenerate = true) val tid: Int = 0,
  @ColumnInfo(name = "image-url") val imageURL: String = "",
  @ColumnInfo(name = "date") val date: String = ""
)