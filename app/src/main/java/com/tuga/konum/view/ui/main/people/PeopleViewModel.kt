package com.tuga.konum.view.ui.main.people

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.tuga.konum.domain.usecase.tracking.TrackingUseCases
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class PeopleViewModel @Inject constructor(
  private val trackingUseCases: TrackingUseCases
) : ViewModel() {
  private val disposable = CompositeDisposable()
  private val trackingDisposable = CompositeDisposable()

  private val location = MutableLiveData<LatLng>()
  private val error = MutableLiveData<String>()

  fun isTracking() = trackingUseCases.isTracking()

  fun startTracker() {
    trackingUseCases.startTracking()
      ?.observeOn(Schedulers.io())
      ?.subscribe(
        this::onLocationReceived,
        this::onError
      )
      ?.let { trackingDisposable.add(it) }
  }

  private fun onLocationReceived(loc: LatLng) {
    location.value = loc
    Timber.d("location: $location")
  }

  private fun onError(exception: Throwable) {
    error.value = exception.message
  }

  fun getCurrentLocation() =
    trackingUseCases.getCurrentLocation()
      ?.observeOn(Schedulers.io())
      ?.subscribe(
        this::onLocationReceived,
        this::onError
      )?.let { disposable.add(it) }
}
