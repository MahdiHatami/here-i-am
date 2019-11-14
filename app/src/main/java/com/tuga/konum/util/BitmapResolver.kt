package com.tuga.konum.util

import android.annotation.TargetApi
import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat.PNG
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.IOException

class BitmapResolver {

  companion object {
    fun getBitmap(contentResolver: ContentResolver, fileUri: Uri): Bitmap {
      return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        getBitmapImageDecoder(contentResolver, fileUri)
      } else {
        getBitmapLegacy(contentResolver, fileUri)
      }
    }

    @SuppressWarnings("deprecation")
    fun getBitmapLegacy(contentResolver: ContentResolver, fileUri: Uri): Bitmap {
      return MediaStore.Images.Media.getBitmap(contentResolver, fileUri)
    }

    @TargetApi(Build.VERSION_CODES.P)
    fun getBitmapImageDecoder(contentResolver: ContentResolver, fileUri: Uri): Bitmap {
      return ImageDecoder.decodeBitmap(ImageDecoder.createSource(contentResolver, fileUri))
    }
    fun convertBitmapToBase64(bitmap: Bitmap): String {
      val byteArrayOutputStream = ByteArrayOutputStream()
      bitmap.compress(PNG, 100, byteArrayOutputStream)
      val byteArray = byteArrayOutputStream.toByteArray()
      return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

  }
}