package com.tuga.konum.domain.models.network

import com.google.android.gms.maps.model.LatLng

data class TrackDto(val trackId: String, val trackDetails: List<LatLng>)