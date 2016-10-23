package abm.ant8.brafhackaton.game

import com.google.android.gms.maps.model.LatLng

interface UserLocationServiceCallback {
    fun showPositions(survivorPosition: List<LatLng>, zombiePositions: List<LatLng>)
    fun getMyPosition(myPosition: LatLng)
}