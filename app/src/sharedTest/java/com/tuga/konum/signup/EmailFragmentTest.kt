package com.tuga.konum.signup

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragment
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
import com.tuga.konum.MainCoroutineRule
import com.tuga.konum.R
import com.tuga.konum.R.style
import com.tuga.konum.models.entity.User
import com.tuga.konum.view.ui.signup.EmailFragment
import com.tuga.konum.view.ui.signup.EmailFragmentArgs
import com.tuga.konum.view.ui.signup.EmailFragmentDirections
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.robolectric.annotation.LooperMode
import org.robolectric.annotation.TextLayoutMode

/**
 * Integration test for the email fragment
 */
@RunWith(AndroidJUnit4::class)
@MediumTest
@LooperMode(LooperMode.Mode.PAUSED)
@TextLayoutMode(TextLayoutMode.Mode.REALISTIC)
@ExperimentalCoroutinesApi
class EmailFragmentTest {

  private var user: User = User()

  // Set the main coroutines dispatcher for unit testing.
  @ExperimentalCoroutinesApi
  @get:Rule
  var mainCoroutineRule = MainCoroutineRule()

  // Executes each task synchronously using Architecture Components.
  @get:Rule
  var instantExecutorRule = InstantTaskExecutorRule()


  @Test
  fun validEmail_navigateToProfileFragment() = runBlockingTest {
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
  fun unValidEmail_shouldDisableButton() = runBlockingTest {
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
    val scenario = launchFragment<EmailFragment>(bundle, style.MaterialTheme)
    scenario.onFragment {
      Navigation.setViewNavController(it.view!!, navController)
    }
  }

}
