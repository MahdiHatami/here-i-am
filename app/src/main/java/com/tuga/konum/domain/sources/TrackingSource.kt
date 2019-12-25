package com.tuga.konum.domain.sources

import android.location.Location
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


interface TrackingSource {

    fun stopTracking()

    fun startTracking() : PublishSubject<Location>

}