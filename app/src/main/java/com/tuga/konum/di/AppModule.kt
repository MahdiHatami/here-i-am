/*
 * The MIT License (MIT)
 *
 * Designed and developed by 2018 skydoves (Jaewoong Eum)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.tuga.konum.di

import android.app.Application
import androidx.room.Room
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.tuga.konum.data.source.local.KonumDatabase
import com.tuga.konum.data.source.local.UserDao
import dagger.Module
import dagger.Provides
import io.reactivex.annotations.NonNull
import okhttp3.OkHttpClient
import javax.inject.Singleton


@Module(includes = [ViewModelModule::class])
class AppModule {
  @Provides
  @Singleton
  fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
      .addNetworkInterceptor(StethoInterceptor())
      .build()
  }

//  @Provides
//  @Singleton
//  fun provideRetrofit(@NonNull okHttpClient: OkHttpClient): Retrofit {
//    return Retrofit.Builder()
//      .client(okHttpClient)
//      .baseUrl("https://api.github.com")
//      .addConverterFactory(GsonConverterFactory.create())
//      .addCallAdapterFactory(LiveDataCallAdapterFactory())
//      .build()
//  }
//
//  @Provides
//  @Singleton
//  fun provideGithubService(@NonNull retrofit: Retrofit): GithubService {
//    return retrofit.create(GithubService::class.java)
//  }

  @Provides
  @Singleton
  fun provideDatabase(@NonNull application: Application): KonumDatabase {
    return Room.databaseBuilder(
      application.applicationContext,
      KonumDatabase::class.java,
      "konum.db"
    )
      .allowMainThreadQueries()
      .build()
  }

  @Provides
  @Singleton
  fun provideUserDao(@NonNull database: KonumDatabase): UserDao {
    return database.userDao()
  }
}
