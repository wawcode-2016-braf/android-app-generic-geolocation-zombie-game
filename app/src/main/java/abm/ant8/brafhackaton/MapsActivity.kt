package abm.ant8.brafhackaton

import android.location.Location
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.util.Log
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.jetbrains.anko.ctx
import org.jetbrains.anko.startService
import org.jetbrains.anko.toast

class MapsActivity : FragmentActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        Log.d("dupa", "proba utworzenia klienta")

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

//    override fun onConnected(p0: Bundle?) {
//        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun onConnectionSuspended(p0: Int) {
//        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }

        this.applicationContext.startService<UserLocationService>()
    }

    override protected fun onStop() {
        super.onStop()
    }

    override fun onStart() {
        Log.d("dupa", "proba polaczenia z klientem")
        super.onStart()
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Warsaw and move the camera
        val warsaw = LatLng(52.0, 21.0)
        mMap.addMarker(MarkerOptions().position(warsaw).title("Marker in Warsaw"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(warsaw))
        mMap.moveCamera(CameraUpdateFactory.zoomTo(12.0f))
    }
}
