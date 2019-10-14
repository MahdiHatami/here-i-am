package com.tuga.konum.view.ui.signup

import android.Manifest
import android.os.Bundle
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.tuga.konum.R
import com.tuga.konum.compose.ViewModelActivity
import com.tuga.konum.databinding.ActivitySignupBinding
import com.tuga.konum.event.RequestStoragePermissionEvent
import com.tuga.konum.permission.PermissionManager
import kotlinx.android.synthetic.main.activity_signup.nav_host_signup_fragment
import kotlinx.android.synthetic.main.activity_signup.toolbar
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.koin.android.viewmodel.ext.android.viewModel

class SignupActivity : ViewModelActivity() {

  private val REQUEST_CODE_READ_EXTERNAL_STORAGE: Int = 100

  private val viewModel by viewModel<SignupActivityViewModel>()
  private val binding by binding<ActivitySignupBinding>(R.layout.activity_signup)

  private val navController: NavController
    get() = findNavController(R.id.nav_host_signup_fragment)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding.viewModel = viewModel

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
    EventBus.getDefault().register(this)
  }

  override fun onStop() {
    super.onStop()
    EventBus.getDefault().unregister(this)
  }

  override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<out String>,
    grantResults: IntArray
  ) {
    when (requestCode) {
      REQUEST_CODE_READ_EXTERNAL_STORAGE -> viewModel.setStoragePermissionStatus(
        PermissionManager().getPermissionStatus(
          this,
          Manifest.permission.READ_EXTERNAL_STORAGE
        )
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
