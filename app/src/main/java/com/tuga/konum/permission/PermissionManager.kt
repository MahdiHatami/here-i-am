package com.tuga.konum.permission

import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.content.pm.PackageManager
import android.app.Activity
import androidx.annotation.NonNull

class PermissionManager {
  fun getPermissionStatus(
    @NonNull activity: Activity,
    @NonNull permission: String
  ): PermissionStatus {
    return when {
      PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(
        activity,
        permission
      ) -> PermissionStatus.PERMISSION_GRANTED
      ActivityCompat.shouldShowRequestPermissionRationale(
        activity,
        permission
      ) -> PermissionStatus.CAN_ASK_PERMISSION
      else -> PermissionStatus.PERMISSION_DENIED
    }
  }
}
