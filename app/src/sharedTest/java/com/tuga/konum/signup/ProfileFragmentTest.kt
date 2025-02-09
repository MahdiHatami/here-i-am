package com.tuga.konum.signup

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.tuga.konum.R
import com.tuga.konum.R.style
import com.tuga.konum.domain.models.entity.User
import com.tuga.konum.view.ui.signup.profile.ProfileFragment
import com.tuga.konum.view.ui.signup.profile.ProfileFragmentArgs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.not
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.annotation.LooperMode
import org.robolectric.annotation.TextLayoutMode

/**
 * integration test for profile screen
 */
@RunWith(AndroidJUnit4::class)
@MediumTest
@LooperMode(LooperMode.Mode.PAUSED)
@TextLayoutMode(TextLayoutMode.Mode.REALISTIC)
@ExperimentalCoroutinesApi
class ProfileFragmentTest {

  private var user = User("0905070933798", "A", "123456", "em@gm.co")

  @Test
  fun validUsername_navigateToLocationPermissionFragment() {
    val navController = Mockito.mock(NavController::class.java)
    launchFragment(navController)

    onView(withId(R.id.edtUsername)).perform(replaceText(user.username))
    onView(withId(R.id.btnProfileNext)).perform(click())

    onView(withId(R.id.btnProfileNext)).check(matches(isEnabled()))

  }

  @Test
  fun emptyUsername_shouldDisableButton() {
    val navController = Mockito.mock(NavController::class.java)
    launchFragment(navController)

    onView(withId(R.id.edtUsername)).perform(replaceText(""))
    onView(withId(R.id.btnProfileNext)).perform(click())

    onView(withId(R.id.btnProfileNext)).check(matches(not(isEnabled())))

  }

  private fun launchFragment(navController: NavController?) {
    val bundle = ProfileFragmentArgs(user).toBundle()
    val scenario = launchFragmentInContainer<ProfileFragment>(bundle, style.Theme_Konum_DayNight)
    scenario.onFragment {
      Navigation.setViewNavController(it.view!!, navController)
    }
  }

}
