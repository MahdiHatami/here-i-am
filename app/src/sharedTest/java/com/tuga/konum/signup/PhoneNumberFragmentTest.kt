package com.tuga.konum.signup

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
import com.tuga.konum.DaggerTestApplicationRule
import com.tuga.konum.R
import com.tuga.konum.models.entity.User
import com.tuga.konum.view.ui.signup.phone.PhoneNumberFragment
import com.tuga.konum.view.ui.signup.phone.PhoneNumberFragmentDirections
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.robolectric.annotation.LooperMode
import org.robolectric.annotation.TextLayoutMode

/**
 * Integration test for phone number fragment
 */

@RunWith(AndroidJUnit4::class)
@MediumTest
@ExperimentalCoroutinesApi
@LooperMode(LooperMode.Mode.PAUSED)
@TextLayoutMode(TextLayoutMode.Mode.REALISTIC)
class PhoneNumberFragmentTest {

  /**
   * Sets up Dagger components for testing.
   */
  @get:Rule
  val rule = DaggerTestApplicationRule()

  private var user = User()

  @Test
  fun validPhoneNumber_navigateToPasswordFragment() {
    // GIVEN - on the profile screen
    val navController = mock(NavController::class.java)
    launchFragment(navController)

    // WHEN - enter valid username length should navigate to location fragment
    user.phoneNumber= "5070933798"
    onView(withId(R.id.edtPhoneNumber)).perform(replaceText(user.phoneNumber))
    onView(withId(R.id.btnNext)).perform(click())

    // THEN - verify that we navigate to location fragment
    verify(navController).navigate(
      PhoneNumberFragmentDirections
        .actionPhoneNumberFragmentToSmsFragment(user)
    )
  }

  @Test
  fun unvalidPhoneNumber_navigateToLocationFragment_shouldDisableButton() {
    // GIVEN - on the profile fragment screen
    val navController = mock(NavController::class.java)
    launchFragment(navController)

    // WHEN = unValid username entered and click next
    val wrongNumber = ""
    onView(withId(R.id.edtPhoneNumber)).perform(replaceText(wrongNumber))

    // THEN - verify the button is disabled
    onView(withId(R.id.btnNext))
      .check(ViewAssertions.matches(CoreMatchers.not((ViewMatchers.isEnabled()))))

  }

  private fun launchFragment(navController: NavController?) {
    val scenario = launchFragmentInContainer<PhoneNumberFragment>(null, R.style.MaterialTheme)
    scenario.onFragment {
      Navigation.setViewNavController(it.view!!, navController)
    }
  }
}
