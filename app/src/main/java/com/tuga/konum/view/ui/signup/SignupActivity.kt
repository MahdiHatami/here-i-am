package com.tuga.konum.view.ui.signup

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.tuga.konum.R
import com.tuga.konum.base.ext.actAsFluid
import com.tuga.konum.databinding.ActivitySignupBinding
import kotlinx.android.synthetic.main.activity_signup.navHostSignupFragment

class SignupActivity : AppCompatActivity(){

  private lateinit var binding: ActivitySignupBinding

  private val navController: NavController
    get() = findNavController(R.id.navHostSignupFragment)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_signup)

    actAsFluid()

    // use label in nav_host for setting fragments title
    navController.addOnDestinationChangedListener { _, _, _ ->
      binding.toolbar.title = navController.currentDestination?.label
      binding.toolbar.setTitleTextColor(android.graphics.Color.WHITE)
    }

    // add Up button
    NavigationUI.setupWithNavController(
      binding.toolbar,
      NavHostFragment.findNavController(navHostSignupFragment)
    )
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return NavigationUI.onNavDestinationSelected(
      item,
      NavHostFragment.findNavController(navHostSignupFragment)
    )
        || super.onOptionsItemSelected(item)
  }

  override fun onSupportNavigateUp(): Boolean {
    return navController.navigateUp()
  }

}
