package com.tuga.konum.di

import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.lifecycle.ViewModel
import com.tuga.konum.view.ui.signup.EmailFragment
import com.tuga.konum.view.ui.signup.LocationPermissionFragment
import com.tuga.konum.view.ui.signup.PasswordFragment
import com.tuga.konum.view.ui.signup.PhoneNumberFragment
import com.tuga.konum.view.ui.signup.ProfileFragment
import com.tuga.konum.view.ui.signup.SignupActivity
import com.tuga.konum.view.ui.signup.SignupActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Dagger module for the phone number feature.
 */
@Module
abstract class SignupModule {

  @ContributesAndroidInjector(
    modules = [
      ViewModelBuilder::class
    ]
  )

//  internal abstract fun passwordFragment(): PasswordFragment
//  internal abstract fun phoneNumberFragment(): PhoneNumberFragment
//
//  internal abstract fun emailFragment(): EmailFragment
//  internal abstract fun profileFragment(): ProfileFragment
//  internal abstract fun locationPermissionFragment(): LocationPermissionFragment
//
  internal abstract fun signupActivity(): SignupActivity
  @Binds
  @IntoMap
  @ViewModelKey(SignupActivityViewModel::class)
  abstract fun bindViewModel(viewmodel: SignupActivityViewModel): ViewModel
}
