package com.tuga.konum.view.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.tuga.konum.R

class MainActivity : AppCompatActivity() {

  private val navController: NavController
    get() = findNavController(R.id.nav_host_main_fragment)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
  }

  override fun onSupportNavigateUp(): Boolean {
    return navController.navigateUp()
  }

  override fun onBackPressed() {

    when (navController.currentDestination?.id) {
      R.id.peopleFragment -> {
        finish()
        moveTaskToBack(true)
      }
      else -> {
        super.onBackPressed()
      }
    }

  }
}
