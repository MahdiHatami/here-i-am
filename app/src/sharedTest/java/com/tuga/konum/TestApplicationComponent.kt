/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tuga.konum

import android.content.Context
import com.tuga.konum.data.source.UserRepository
import com.tuga.konum.di.EmailModule
import com.tuga.konum.di.LocationPermissionModule
import com.tuga.konum.di.PasswordModule
import com.tuga.konum.di.PhoneNumberModule
import com.tuga.konum.di.ProfileModule
import com.tuga.konum.di.SmsModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
  modules = [
    TestApplicationModule::class,
    AndroidSupportInjectionModule::class,
    PhoneNumberModule::class,
    SmsModule::class,
    PasswordModule::class,
    EmailModule::class,
    ProfileModule::class,
    LocationPermissionModule::class
  ]
)
interface TestApplicationComponent : AndroidInjector<TestKonumApplication> {

  @Component.Factory
  interface Factory {
    fun create(@BindsInstance applicationContext: Context): TestApplicationComponent
  }

  val userRepository: UserRepository
}
