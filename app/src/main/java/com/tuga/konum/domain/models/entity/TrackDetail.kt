package com.tuga.konum.domain.models.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
  tableName = "trackDetail",
  foreignKeys = [ForeignKey(
    entity = Track::class,
    parentColumns = arrayOf("tid"),
    childColumns = arrayOf("trackId"),
    onDelete = ForeignKey.CASCADE
  )]
)
data class TrackDetail(
  @PrimaryKey(autoGenerate = true) val tdId: Int? = null,
  @ColumnInfo(name = "trackId", index = true) val trackId: Int? = null,
  @ColumnInfo(name = "long") val y: String?,
  @ColumnInfo(name = "lat") val x: String?
)