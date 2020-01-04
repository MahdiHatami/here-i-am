package com.tuga.konum.data

import android.content.SharedPreferences
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import javax.inject.Inject

class PrefStorage @Inject constructor(
  private val sharedPreferences: SharedPreferences,
  private val gson: Gson
) {
  companion object {
    private const val isTracking = "isTracking"
    private const val lastLocation = "lastLocation"
  }

  fun writeIsTracking(value: Boolean) {
    val prefEditor = sharedPreferences.edit()
    prefEditor.putBoolean(isTracking, value)
    prefEditor.apply()
  }

  fun readIsTracking() = sharedPreferences.getBoolean(isTracking, false)

  fun writeLastLocation(value: LatLng) {
    val prefEditor = sharedPreferences.edit()
    prefEditor.putStringSet(
      lastLocation,
      setOf(value.latitude.toString(), value.longitude.toString())
    )
    prefEditor.apply()
  }

  fun readLastLocation(): LatLng? {
    var location: LatLng? = null
    val set = sharedPreferences.getStringSet(lastLocation, setOf(""))
    set?.let {
      if (!set.contains(""))
        location = LatLng(set.toList()[0].toDouble(), set.toList()[1].toDouble())
    }
    return location
  }

}