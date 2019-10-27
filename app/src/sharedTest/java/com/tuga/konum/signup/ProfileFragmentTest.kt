package com.tuga.konum.signup

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.tuga.konum.R
import com.tuga.konum.R.id
import com.tuga.konum.R.style
import com.tuga.konum.models.entity.User
import com.tuga.konum.view.ui.signup.ProfileFragment
import com.tuga.konum.view.ui.signup.ProfileFragmentArgs
import com.tuga.konum.view.ui.signup.ProfileFragmentDirections
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
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
@ExperimentalCoroutinesApi
class ProfileFragmentTest {

  private var user: User = User()

  @Test
  fun validUsername_navigateToLocationFragment() = runBlockingTest {
    // GIVEN - on the profile screen
    val navController = mock(NavController::class.java)
    launchFragment(navController)

    // WHEN - enter valid username length should navigate to location fragment
    user.username = "Mahdi"
    onView(withId(id.edtUsername)).perform(replaceText(user.username))
    onView(withId(id.btnProfileNext)).perform(click())

    // THEN - verify that we navigate to location fragment
    verify(navController).navigate(
      ProfileFragmentDirections.actionProfileFragmentToLocationPermissionFragment()
    )
  }

  @Test
  fun unValidUsername_shouldDisableButton() = runBlockingTest {
    // GIVEN - on the profile fragment screen
    val navController = mock(NavController::class.java)
    launchFragment(navController)

    // WHEN = unValid username entered and click next
    val unValidUsername = ""
    onView(withId(R.id.edtUsername)).perform(replaceText(unValidUsername))

    // THEN - verify the button is disabled
    onView(withId(R.id.btnProfileNext))
      .check(ViewAssertions.matches(CoreMatchers.not((ViewMatchers.isEnabled()))))

  }

  private fun launchFragment(navController: NavController?) {
    val bundle = ProfileFragmentArgs(user).toBundle()
    val scenario = launchFragmentInContainer<ProfileFragment>(Bundle(), style.MaterialTheme)
    scenario.onFragment {
      Navigation.setViewNavController(it.view!!, navController)
    }
  }
}
