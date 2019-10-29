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
import androidx.test.rule.ActivityTestRule
import com.tuga.konum.R
import com.tuga.konum.SingleFragmentActivity
import com.tuga.konum.models.entity.User
import com.tuga.konum.util.CountingAppExecutorsRule
import com.tuga.konum.util.DataBindingIdlingResourceRule
import com.tuga.konum.util.TaskExecutorWithIdlingResourceRule
import com.tuga.konum.view.ui.signup.PhoneNumberFragment
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.robolectric.annotation.TextLayoutMode

/**
 * Integration test for the phone number fragment
 */
@MediumTest
@TextLayoutMode(TextLayoutMode.Mode.REALISTIC)
@RunWith(AndroidJUnit4::class)
class PhoneNumberFragmentTest {

  @Rule
  @JvmField
  val activityRule = ActivityTestRule(SingleFragmentActivity::class.java, true, true)
  @Rule
  @JvmField
  val executorRule = TaskExecutorWithIdlingResourceRule()
  @Rule
  @JvmField
  val countingAppExecutors = CountingAppExecutorsRule()
  @Rule
  @JvmField
  val dataBindingIdlingResourceRule = DataBindingIdlingResourceRule(activityRule)

  private var user: User = User()

  @Test
  fun validPhoneNumber_navigateToPasswordFragment() {
    // GIVEN - on the phone number fragment screen
    val navController = mock(NavController::class.java)
    launchFragment(navController)

    // WHEN = valid phone number entered and click next
    val validPhoneNumber = "5070933798"
    user.phoneNumber = validPhoneNumber
    onView(withId(R.id.edtPhoneNumber)).perform(replaceText(validPhoneNumber))
    onView(withId(R.id.btnNext)).perform(click())

    // THEN - verify that we navigate to password fragment
    onView(withId(R.id.btnNext)).check(matches((isEnabled())))

  }

  @Test
  fun wrongPhoneNumber_couldDisableButton() {
    // GIVEN - on the phone number fragment screen
    val navController = mock(NavController::class.java)
    launchFragment(navController)

    // WHEN = valid phone number entered and click next
    val unValidPhoneNumber = "5070933799999"
    onView(withId(R.id.edtPhoneNumber)).perform(replaceText(unValidPhoneNumber))

    // THEN - verify the button is disabled
    onView(withId(R.id.btnNext)).check(matches(not((isEnabled()))))

  }

  private fun launchFragment(navController: NavController?) {
    val bundle = null
    val scenario = launchFragmentInContainer<PhoneNumberFragment>(bundle, R.style.MaterialTheme)
    scenario.onFragment {
      Navigation.setViewNavController(it.view!!, navController)
    }
  }
}
