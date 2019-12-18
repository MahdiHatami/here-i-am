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
import com.tuga.konum.R.id
import com.tuga.konum.R.style
import com.tuga.konum.domain.models.entity.User
import com.tuga.konum.view.ui.signup.smsVerfication.SmsFragment
import com.tuga.konum.view.ui.signup.smsVerfication.SmsFragmentArgs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.annotation.LooperMode
import org.robolectric.annotation.TextLayoutMode

@RunWith(AndroidJUnit4::class)
@MediumTest
@ExperimentalCoroutinesApi
@LooperMode(LooperMode.Mode.PAUSED)
@TextLayoutMode(TextLayoutMode.Mode.REALISTIC)
class SmsFragmentTest {

  private var user = User("5070933798")

  @Test
  fun validCode_shouldEnableButton() {

    val navController = Mockito.mock(NavController::class.java)
    launchFragment(navController)

    onView(withId(id.etVerificationCode1)).perform(replaceText("1"))
    onView(withId(id.etVerificationCode2)).perform(replaceText("2"))
    onView(withId(id.etVerificationCode3)).perform(replaceText("3"))
    onView(withId(id.etVerificationCode4)).perform(replaceText("4"))

    onView(withId(id.btnVerify)).perform(click())

    onView(withId(id.btnVerify)).check(ViewAssertions.matches(ViewMatchers.isEnabled()))
  }

  private fun launchFragment(navController: NavController?) {
    val bundle = SmsFragmentArgs(user).toBundle()
    val scenario = launchFragmentInContainer<SmsFragment>(bundle, style.Theme_Konum_DayNight)
    scenario.onFragment {
      Navigation.setViewNavController(it.view!!, navController)
    }
  }
}
