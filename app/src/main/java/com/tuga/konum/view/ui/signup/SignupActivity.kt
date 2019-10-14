package com.tuga.konum.view.ui.signup

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.tuga.konum.R
import com.tuga.konum.compose.ViewModelActivity
import kotlinx.android.synthetic.main.activity_signup.nav_host_signup_fragment
import kotlinx.android.synthetic.main.activity_signup.toolbar

class SignupActivity : ViewModelActivity() {

  private val navController: NavController
    get() = findNavController(R.id.nav_host_signup_fragment)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_signup)

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

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

    super.onActivityResult(requestCode, resultCode, data)
  }
}
