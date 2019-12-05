package com.tuga.konum.signup

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.tuga.konum.R.id
import com.tuga.konum.R.style
import com.tuga.konum.domain.models.entity.User
import com.tuga.konum.view.ui.signup.password.PasswordFragment
import com.tuga.konum.view.ui.signup.password.PasswordFragmentArgs
import com.tuga.konum.view.ui.signup.password.PasswordFragmentDirections
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.annotation.LooperMode
import org.robolectric.annotation.TextLayoutMode

/**
 * integration test for password screen
 */
@RunWith(AndroidJUnit4::class)
@MediumTest
@LooperMode(LooperMode.Mode.PAUSED)
@TextLayoutMode(TextLayoutMode.Mode.REALISTIC)
@ExperimentalCoroutinesApi
class PasswordFragmentTest {

  private var user = User()

  @Test
  fun validPassword_navigateToEmailFragment() {
    // GIVEN - on the choose password screen
    val navController = Mockito.mock(NavController::class.java)
    launchFragment(navController)

    // WHEN - enter valid password length should navigate to email fragment
    val validPass = "123456"
    Espresso.onView(ViewMatchers.withId(id.edtPassword)).perform(ViewActions.replaceText(validPass))
    Espresso.onView(ViewMatchers.withId(id.btnPasswordNext)).perform(ViewActions.click())

    // THEN - verify that we navigate to email fragment
    Mockito.verify(navController).navigate(
      PasswordFragmentDirections.actionPasswordFragmentToEmailFragment(user)
    )
  }

  @Test
  fun unValidPassword_shouldDisableButton() {
    // GIVEN - on the phone number fragment screen
    val navController = Mockito.mock(NavController::class.java)
    launchFragment(navController)

    // WHEN = valid phone number entered and click next
    val unValidPassword = "123"
    Espresso.onView(ViewMatchers.withId(id.edtPassword))
      .perform(ViewActions.replaceText(unValidPassword))

    // THEN - verify the button is disabled
    Espresso.onView(ViewMatchers.withId(id.btnPasswordNext))
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
