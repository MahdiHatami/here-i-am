package com.tuga.konum.signup

import android.content.Context
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.tuga.konum.R
import com.tuga.konum.R.string
import com.tuga.konum.R.style
import com.tuga.konum.models.entity.User
import com.tuga.konum.view.ui.signup.PasswordFragment
import com.tuga.konum.view.ui.signup.PasswordFragmentArgs
import com.tuga.konum.view.ui.signup.PasswordFragmentDirections
import com.tuga.konum.view.ui.signup.ProfileFragmentDirections
import org.hamcrest.CoreMatchers
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.robolectric.annotation.TextLayoutMode

/**
 * Integration test for the profile fragment
 */
@MediumTest
@TextLayoutMode(TextLayoutMode.Mode.REALISTIC)
@RunWith(AndroidJUnit4::class)
class ProfileFragmentTest {

  private var user: User = User()

  @Test
  fun validUsername_navigateToLocationFragment() {
    // GIVEN - on the profile screen
    val navController = mock(NavController::class.java)
    launchFragment(navController)

    // WHEN - enter valid username length should navigate to location fragment
    user.username = "Mahdi"
    onView(withId(R.id.edtUsername)).perform(replaceText(user.username))
    onView(withId(R.id.btnProfileNext)).perform(click())

    // THEN - verify that we navigate to location fragment
    verify(navController).navigate(
      ProfileFragmentDirections.actionProfileFragmentToLocationPermissionFragment()
    )
  }

  @Test
  fun unValidPassword_couldDisableButton() {
    // GIVEN - on the profile fragment screen
    val navController = mock(NavController::class.java)
    launchFragment(navController)

    // WHEN = unValid username entered and click next
    val unValidUsername = ""
    onView(withId(R.id.edtPassword)).perform(replaceText(unValidUsername))

    // THEN - verify the button is disabled
    onView(withId(R.id.btnProfileNext))
      .check(ViewAssertions.matches(CoreMatchers.not((ViewMatchers.isEnabled()))))

  }

  private fun launchFragment(navController: NavController?) {
    val bundle = PasswordFragmentArgs(user).toBundle()
    val scenario = launchFragmentInContainer<PasswordFragment>(bundle, style.MaterialTheme)
    scenario.onFragment {
      Navigation.setViewNavController(it.view!!, navController)
    }
  }
}
