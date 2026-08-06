package org.shojhiseb.shared.core.location

data class LocationResult(val latitude: Double, val longitude: Double)

expect class LocationService() {
    suspend fun getCurrentLocation(): LocationResult?
}
