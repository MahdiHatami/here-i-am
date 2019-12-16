package com.tuga.konum.view.ui.main

import android.app.usage.UsageEvents.Event.NONE
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.MenuRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.tuga.konum.R
import com.tuga.konum.R.menu
import com.tuga.konum.databinding.ActivityMainBinding
import com.tuga.konum.view.material.BottomNavDrawerFragment
import com.tuga.konum.view.material.ChangeSettingsMenuStateAction
import com.tuga.konum.view.material.ShowHideFabStateAction
import com.tuga.konum.view.material.contentView
import com.tuga.konum.view.ui.compose.ComposeFragmentDirections

class MainActivity : AppCompatActivity(), Toolbar.OnMenuItemClickListener,
  NavController.OnDestinationChangedListener {

  private val navController: NavController
    get() = findNavController(R.id.nav_host_main_fragment)

  private var currentEmailId = -1L

  private val binding: ActivityMainBinding by contentView(R.layout.activity_main)
  private val bottomNavDrawer: BottomNavDrawerFragment by lazy(NONE) {
    supportFragmentManager.findFragmentById(R.id.bottom_nav_drawer) as BottomNavDrawerFragment
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setUpBottomNavigationAndFab()
  }

  private fun setUpBottomNavigationAndFab() {
    // Wrap binding.run to ensure ContentViewBindingDelegate is calling this Activity's
    // setContentView before accessing views
    binding.run {
      findNavController(R.id.nav_host_main_fragment).addOnDestinationChangedListener(
        this@MainActivity
      )
    }

    // Set a custom animation for showing and hiding the FAB
    binding.fab.apply {
      setShowMotionSpecResource(R.animator.fab_show)
      setHideMotionSpecResource(R.animator.fab_hide)
      setOnClickListener {
        findNavController(R.id.nav_host_main_fragment)
          .navigate(ComposeFragmentDirections.actionGlobalComposeFragment(currentEmailId))
      }
    }

    bottomNavDrawer.apply {
//      addOnSlideAction(HalfClockwiseRotateSlideAction(binding.bottomAppBarChevron))
//      addOnSlideAction(AlphaSlideAction(binding.bottomAppBarTitle, true))
      addOnStateChangedAction(ShowHideFabStateAction(binding.fab))
      addOnStateChangedAction(ChangeSettingsMenuStateAction { showSettings ->
        // Toggle between the current destination's BAB menu and the menu which should
        // be displayed when the BottomNavigationDrawer is open.
        binding.bottomAppBar.replaceMenu(
          if (showSettings) {
            menu.bottom_app_bar_settings_menu
          } else {
            getBottomAppBarMenuForDestination()
          }
        )
      })

//      addOnSandwichSlideAction(HalfCounterClockwiseRotateSlideAction(binding.bottomAppBarChevron))
    }

    // Set up the BottomAppBar menu
    binding.bottomAppBar.apply {
      setNavigationOnClickListener {
        bottomNavDrawer.toggle()
      }
      setOnMenuItemClickListener(this@MainActivity)
    }

    // Set up the BottomNavigationDrawer's open/close affordance
    binding.bottomAppBarContentContainer.setOnClickListener {
      bottomNavDrawer.toggle()
    }
  }

  /**
   * Helper function which returns the menu which should be displayed for the current
   * destination.
   *
   * Used both when the destination has changed, centralizing destination-to-menu mapping, as
   * well as switching between the alternate menu used when the BottomNavigationDrawer is
   * open and closed.
   */
  @MenuRes
  private fun getBottomAppBarMenuForDestination(destination: NavDestination? = null): Int {
    val dest = destination ?: findNavController(R.id.nav_host_main_fragment).currentDestination
    return when (dest?.id) {
      R.id.peopleFragment -> menu.menu_main
      R.id.emailFragment -> menu.bottom_app_bar_email_menu
      else -> menu.bottom_app_bar_home_menu
    }
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

  override fun onMenuItemClick(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.menu_settings -> {
        bottomNavDrawer.close()
      }
    }
    return true
  }

  override fun onDestinationChanged(
    controller: NavController,
    destination: NavDestination,
    arguments: Bundle?
  ) {
    // Set the currentEmail being viewed so when the FAB is pressed, the correct email
    // reply is created. In a real app, this should be done in a ViewModel but is done
    // here to keep things simple. Here we're also setting the configuration of the
    // BottomAppBar and FAB based on the current destination.
    when (destination.id) {
      R.id.peopleFragment -> {
        currentEmailId = -1
        setBottomAppBarForHome(getBottomAppBarMenuForDestination(destination))
      }
      R.id.emailFragment -> {
        setBottomAppBarForEmail(getBottomAppBarMenuForDestination(destination))
      }
      R.id.composeFragment -> {
        currentEmailId = -1
        setBottomAppBarForCompose()
      }
    }
  }

  private fun setBottomAppBarForHome(@MenuRes menuRes: Int) {
    binding.run {
      fab.setImageState(intArrayOf(-android.R.attr.state_activated), true)
      bottomAppBar.visibility = View.VISIBLE
      bottomAppBar.replaceMenu(menuRes)
      fab.contentDescription = getString(R.string.fab_compose_email_content_description)
//      bottomAppBarTitle.visibility = View.VISIBLE
      bottomAppBar.performShow()
      fab.show()
    }
  }

  private fun setBottomAppBarForEmail(@MenuRes menuRes: Int) {
    binding.run {
      fab.setImageState(intArrayOf(android.R.attr.state_activated), true)
      bottomAppBar.visibility = View.VISIBLE
      bottomAppBar.replaceMenu(menuRes)
      fab.contentDescription = getString(R.string.fab_reply_email_content_description)
//      bottomAppBarTitle.visibility = View.INVISIBLE
      bottomAppBar.performShow()
      fab.show()
    }
  }

  private fun setBottomAppBarForCompose() {
    binding.run {
      bottomAppBar.performHide()
      fab.hide()
      // Hide the BottomAppBar to avoid it showing above the keyboard
      // when composing a new email.
      bottomAppBar.visibility = View.GONE
    }
  }

}
