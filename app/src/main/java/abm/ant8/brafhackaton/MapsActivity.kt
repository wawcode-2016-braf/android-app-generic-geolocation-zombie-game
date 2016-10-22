package abm.ant8.brafhackaton

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.util.Log
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.jetbrains.anko.ctx
import org.jetbrains.anko.toast

class MapsActivity : FragmentActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var mGoogleApiClient: GoogleApiClient? = null
    private var googleApiConnected: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        if (mGoogleApiClient == null) {
            mGoogleApiClient = GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(object : GoogleApiClient.ConnectionCallbacks {
                        override fun onConnected(p0: Bundle?) {
                            Log.d("BRAF", "połączono")
                            var mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                                    mGoogleApiClient)
                            if (mLastLocation != null) {
                                ctx.toast(mLastLocation.getLatitude().toString())
                                Log.d("dupa", mLastLocation.getLatitude().toString())
                                ctx.toast(mLastLocation.getLongitude().toString())
                                Log.d("dupa", mLastLocation.getLongitude().toString())
                            }
                        }
                        override fun onConnectionSuspended(p0: Int) {
                            Log.d("BRAF", "zawieszono")
                        }
                    })
                    .addOnConnectionFailedListener { toast("błąd komunikacji z serwerem") }
                    .addApi(LocationServices.API)
                    .build()
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
//    override fun onConnected(p0: Bundle?) {
//        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun onConnectionSuspended(p0: Int) {
//        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }

    override protected fun onStart() {
        mGoogleApiClient?.connect()
        super.onStart()
    }

    override protected fun onStop() {
        mGoogleApiClient?.disconnect()
        super.onStop()
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
