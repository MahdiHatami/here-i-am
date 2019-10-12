package com.tuga.konum.view.ui.signup

import android.Manifest
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.tuga.konum.R
import com.tuga.konum.permission.PermissionManager
import kotlinx.android.synthetic.main.activity_signup.nav_host_signup_fragment
import kotlinx.android.synthetic.main.activity_signup.toolbar
import org.greenrobot.eventbus.EventBus
import org.koin.android.viewmodel.ext.android.viewModel
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import com.tuga.konum.event.RequestStoragePermissionEvent
import com.tuga.konum.view.ui.signup.SignupActivityViewModel.Companion
import org.greenrobot.eventbus.Subscribe

class SignupActivity : AppCompatActivity() {
  private val REQUEST_CODE_READ_EXTERNAL_STORAGE: Int = 100

  private val navController: NavController
    get() = findNavController(R.id.nav_host_signup_fragment)

  private val viewModel by viewModel<SignupActivityViewModel>()

  private lateinit var eventBus: EventBus
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_signup)
    eventBus = EventBus()

    // use label in nav_host for setting fragments title
    navController.addOnDestinationChangedListener { controller, destination, arguments ->
      toolbar.title = navController.currentDestination?.label
    }

    // add Up button
    NavigationUI.setupWithNavController(
      toolbar,
      NavHostFragment.findNavController(nav_host_signup_fragment)
    )
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return NavigationUI.onNavDestinationSelected(
      item,
      NavHostFragment.findNavController(nav_host_signup_fragment)
    )
        || super.onOptionsItemSelected(item)
  }

  override fun onSupportNavigateUp(): Boolean {
    return navController.navigateUp()
  }

  override fun onStart() {
    super.onStart()
    eventBus.register(this)
    SignupActivityViewModel.setStoragePermissionStatus(
      viewModel, PermissionManager().getPermissionStatus(
        this,
        Manifest.permission.READ_EXTERNAL_STORAGE
      )
    )

  }

  override fun onStop() {
    super.onStop()
    eventBus.unregister(this)
  }

  override fun onRequestPermissionsResult(
    requestCode: Int,
    @NonNull permissions: Array<String>,
    @NonNull grantResults: IntArray
  ) {
    when (requestCode) {
      REQUEST_CODE_READ_EXTERNAL_STORAGE -> viewModel.storagePermissionStatus =
        PermissionManager().getPermissionStatus(
          this,
          Manifest.permission.READ_EXTERNAL_STORAGE
        )

      else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
  }

  @Subscribe
  fun onRequestStoragePermissionEvent(event: RequestStoragePermissionEvent) {
    ActivityCompat.requestPermissions(
      this,
      arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
      REQUEST_CODE_READ_EXTERNAL_STORAGE
    )
  }
}
