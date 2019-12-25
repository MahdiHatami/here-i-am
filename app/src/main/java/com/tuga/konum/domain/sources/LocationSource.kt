package com.tuga.konum.domain.sources

import android.location.Location
import io.reactivex.subjects.PublishSubject

interface LocationSource {

    fun getCurrentLocation() : PublishSubject<Location>

}