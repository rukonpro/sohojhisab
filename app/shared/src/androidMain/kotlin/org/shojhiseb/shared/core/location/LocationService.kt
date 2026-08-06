package org.shojhiseb.shared.core.location

import kotlinx.coroutines.delay

actual class LocationService actual constructor() {
    actual suspend fun getCurrentLocation(): LocationResult? {
        // Mocking a delay to simulate location fetching
        delay(500)
        // Mock location: Dhaka, Bangladesh
        return LocationResult(latitude = 23.8103, longitude = 90.4125)
    }
}
