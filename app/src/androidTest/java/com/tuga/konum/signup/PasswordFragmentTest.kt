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
import com.tuga.konum.view.ui.signup.PasswordFragment
import com.tuga.konum.view.ui.signup.PasswordFragmentArgs
import com.tuga.konum.view.ui.signup.PasswordFragmentDirections
import kotlinx.android.synthetic.main.fragment_password.view.edtPassword
import org.hamcrest.CoreMatchers
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.robolectric.annotation.TextLayoutMode

/**
 * Integration test for the phone number fragment
 */
@MediumTest
@TextLayoutMode(TextLayoutMode.Mode.REALISTIC)
@RunWith(AndroidJUnit4::class)
class PasswordFragmentTest {

  @Test
  fun validPassword_navigateToEmailFragment() {
    // GIVEN - on the choose password screen
    val navController = mock(NavController::class.java)
    launchFragment(navController)

    // WHEN - enter valid password length should navigate to email fragment
    val validPass = "123456"
    onView(withId(R.id.edtPassword)).perform(replaceText(validPass))
    onView(withId(R.id.btnPasswordNext)).perform(click())

    // THEN - verify that we navigate to email fragment
    verify(navController).navigate(
      PasswordFragmentDirections.actionPasswordFragmentToEmailFragment(validPass)
    )
  }

  @Test
  fun unValidPassword_couldDisableButton() {
    // GIVEN - on the phone number fragment screen
    val navController = mock(NavController::class.java)
    launchFragment(navController)

    // WHEN = valid phone number entered and click next
    val unValidPassword = "123"
    onView(withId(R.id.edtPassword)).perform(replaceText(unValidPassword))

    // THEN - verify the button is disabled
    onView(withId(R.id.btnPasswordNext))
      .check(ViewAssertions.matches(CoreMatchers.not((ViewMatchers.isEnabled()))))

  }

  private fun launchFragment(navController: NavController?) {
    val bundle = PasswordFragmentArgs(
      ApplicationProvider.getApplicationContext<Context>().getString(string.phone_number_title)
    ).toBundle()
    val scenario = launchFragmentInContainer<PasswordFragment>(bundle, style.MaterialTheme)
    scenario.onFragment {
      Navigation.setViewNavController(it.view!!, navController)
    }
  }
}
