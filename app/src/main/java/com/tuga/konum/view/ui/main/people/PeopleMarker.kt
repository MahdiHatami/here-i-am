package com.tuga.konum.view.ui.main.people

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.Config.ARGB_8888
import android.graphics.Canvas
import android.graphics.Rect
import android.os.Build.VERSION_CODES
import android.os.Handler
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.PixelCopy
import android.view.View
import android.view.ViewGroup.LayoutParams
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import com.tuga.konum.R
import de.hdodenhof.circleimageview.CircleImageView
import javax.inject.Inject

class PeopleMarker @Inject constructor(
  private val context: Context
) {

  @RequiresApi(VERSION_CODES.O)
  fun getBitmapFromView(
    layoutId: Int,
    resource: Int,
    callback: (Bitmap) -> Unit
  ) {
    val view: View =
      (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
        layoutId,
        null
      )
    (view.findViewById<View>(resource) as CircleImageView).apply {
      setImageResource(resource)
    }

    view.layoutParams = LayoutParams(50, LayoutParams.WRAP_CONTENT)
    (context as Activity).window?.let { window ->
      val bitmap = Bitmap.createBitmap(view.width, view.height, ARGB_8888)
      val locationOfViewInWindow = IntArray(2)
      view.getLocationInWindow(locationOfViewInWindow)
      try {
        PixelCopy.request(
          window, Rect(
            locationOfViewInWindow[0],
            locationOfViewInWindow[1],
            locationOfViewInWindow[0] + view.width,
            locationOfViewInWindow[1] + view.height
          ), bitmap, { copyResult ->
            if (copyResult == PixelCopy.SUCCESS) {
              val canvas = Canvas(bitmap)
              view.draw(canvas)
              callback(bitmap)
            }
            // possible to handle other result codes ...
          }, Handler()
        )
      } catch (e: IllegalArgumentException) {
        // PixelCopy may throw IllegalArgumentException, make sure to handle it
        e.printStackTrace()
      }
    }
  }

  private fun createMarker(@NonNull @DrawableRes resource: Int, @NonNull @IdRes layoutId: Int): Bitmap {
    val markerView: View =
      (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
        .inflate(R.layout.custom_marker_layout, null)
    (markerView.findViewById<View>(layoutId) as CircleImageView).apply {
      setImageResource(resource)
    }

    val displayMetrics = DisplayMetrics()
    (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
    markerView.layoutParams = LayoutParams(52, LayoutParams.WRAP_CONTENT)
    markerView.measure(0, 0)
    markerView.layout(0, 0, 0, 0)
    markerView.buildDrawingCache()

    val bitmap: Bitmap = convertMarkerLayoutToBitmap(markerView)
    val canvas = Canvas(bitmap)
    markerView.draw(canvas)
    return bitmap
  }

  private fun convertMarkerLayoutToBitmap(marker: View): Bitmap {
    val bitmap: Bitmap = Bitmap.createBitmap(
      marker.measuredWidth,
      marker.measuredHeight,
      ARGB_8888
    )
    return bitmap
  }
}