package com.tuga.konum.view.ui.main.people

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.Config.ARGB_8888
import android.graphics.Canvas
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.NonNull
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.tuga.konum.databinding.CustomMarkerLayoutBinding
import javax.inject.Inject

class PeopleMarker @Inject constructor(
  private val context: Context
) {

  fun createMarker(
    @NonNull @DrawableRes userImage: Int,
    @NonNull position: LatLng,
    callback: (MarkerOptions) -> Unit
  ) {
    val bitmap: Bitmap = getBitmapFromView(userImage)
    val marker = MarkerOptions()
      .position(position)
      .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
    callback(marker)
  }

  private fun getBitmapFromView(userImage: Int): Bitmap {
    val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val markerBinding = CustomMarkerLayoutBinding.inflate(inflater).apply {
      markerUserIcon.setImageResource(userImage)
    }
    val view: View = markerBinding.root.apply {
      measure(DisplayMetrics().widthPixels, DisplayMetrics().heightPixels)
      layout(0, 0, 0, 0)
      buildLayer()
    }
    val bitmap: Bitmap = Bitmap.createBitmap(
      view.measuredWidth,
      view.measuredHeight,
      ARGB_8888
    )
    val canvas = Canvas(bitmap)
    view.draw(canvas)
    return bitmap
  }
}
