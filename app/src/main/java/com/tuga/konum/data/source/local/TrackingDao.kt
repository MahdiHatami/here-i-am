package com.tuga.konum.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tuga.konum.domain.models.entity.Track
import com.tuga.konum.domain.models.entity.TrackDetail

@Dao
interface TrackingDao {

  @Insert
  fun insertTrack(track: Track): Long

  @Insert
  fun insertTrackDetail(trackDetail: TrackDetail): Long

  @Query("SELECT * FROM track")
  fun getAllTracks(): List<Track>

  @Query("SELECT * FROM trackDetail WHERE trackId = :trackId")
  fun getTrackDetails(trackId: Int): List<TrackDetail>

  @Query("DELETE FROM track WHERE tid = :trackId")
  fun deleteTrack(trackId: Long)
}