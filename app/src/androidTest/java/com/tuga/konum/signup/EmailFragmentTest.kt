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
import com.tuga.konum.R
import com.tuga.konum.R.style
import com.tuga.konum.models.entity.User
import com.tuga.konum.view.ui.signup.EmailFragment
import com.tuga.konum.view.ui.signup.EmailFragmentArgs
import com.tuga.konum.view.ui.signup.EmailFragmentDirections
import org.hamcrest.CoreMatchers
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.robolectric.annotation.TextLayoutMode

/**
 * Integration test for the email fragment
 */
@MediumTest
@TextLayoutMode(TextLayoutMode.Mode.REALISTIC)
@RunWith(AndroidJUnit4::class)
class EmailFragmentTest {

  private var user: User = User()

  @Test
  fun validEmail_navigateToProfileFragment() {
    // GIVEN - on the choose email screen
    val navController = mock(NavController::class.java)
    launchFragment(navController)

    // WHEN - enter valid email length should navigate to email fragment
    user.email = "iz.hatami@gmail.com"
    onView(withId(R.id.edtEmail)).perform(replaceText(user.email))
    onView(withId(R.id.btnEmailNext)).perform(click())

    // THEN - verify that we navigate to email fragment
    verify(navController).navigate(
      EmailFragmentDirections.actionEmailFragmentToProfileFragment(user)
    )
  }

  @Test
  fun unValidEmail_shouldDisableButton() {
    // GIVEN - on the email fragment screen
    val navController = mock(NavController::class.java)
    launchFragment(navController)

    // WHEN = unValid email entered and click next
    val validEmail = "asdf@asdf."
    onView(withId(R.id.edtEmail)).perform(replaceText(validEmail))

    // THEN - verify the button is disabled
    onView(withId(R.id.btnEmailNext))
      .check(ViewAssertions.matches(CoreMatchers.not((ViewMatchers.isEnabled()))))

  }

  private fun launchFragment(navController: NavController?) {
    val bundle = EmailFragmentArgs(user).toBundle()
    val scenario = launchFragmentInContainer<EmailFragment>(bundle, style.MaterialTheme)
    scenario.onFragment {
      Navigation.setViewNavController(it.view!!, navController)
    }
  }
}
