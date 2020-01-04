package com.tuga.konum.util

import com.google.android.gms.maps.model.LatLng
import java.util.ArrayList;
import java.util.Arrays;

object PolylineEncoding {

  /** Encodes a sequence of LatLngs into an encoded path string.  */
  fun encode(path: List<LatLng>): String {
    var lastLat: Long = 0
    var lastLng: Long = 0

    val result = StringBuilder()

    for (point in path) {
      val lat = Math.round(point.latitude * 1e5)
      val lng = Math.round(point.longitude * 1e5)

      val dLat = lat - lastLat
      val dLng = lng - lastLng

      encode(dLat, result)
      encode(dLng, result)

      lastLat = lat
      lastLng = lng
    }
    return result.toString()
  }

  private fun encode(v: Long, result: StringBuilder) {
    var v = v
    v = if (v < 0) (v shl 1).inv() else v shl 1
    while (v >= 0x20) {
      result.append(Character.toChars((0x20 or ((v and 0x1f).toInt())) + 63))
      v = v shr 5
    }
    result.append(Character.toChars((v + 63).toInt()))
  }
}