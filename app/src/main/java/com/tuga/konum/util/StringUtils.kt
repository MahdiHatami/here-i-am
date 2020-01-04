package com.tuga.konum.util

import com.google.android.gms.maps.model.LatLng
import com.tuga.konum.util.PolylineEncoding.encode

object StringUtils {
  private val apiKey = "AIzaSyCbj_oJdGf5VskyIOEB_2zq1z_e9EN51Kc"

  fun makeMapUrl(list: List<LatLng>) =
    "https://maps.googleapis.com/maps/api/staticmap?size=1000x1000&zoom=19&path=weight:7%7Ccolor:blue%7Cenc:${encode(
      list
    )}&key=$apiKey"

}