package abm.ant8.brafhackaton

import abm.ant8.brafhackaton.game.UserLocationServiceCallback
import abm.ant8.brafhackaton.login.LogoutActivity
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.os.IBinder
import android.support.v4.app.FragmentActivity
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.jetbrains.anko.*

class MapsActivity : FragmentActivity(), OnMapReadyCallback, UserLocationServiceCallback {
    override fun getMyPosition(myPosition: LatLng) {
        myMarker = myPosition
        mMap.clear()
        mMap.addMarker(MarkerOptions().position(myMarker).title("Marker in Warsaw"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(myMarker))
        mMap.moveCamera(CameraUpdateFactory.zoomTo(12.0f))
    }

    private lateinit var mMap: GoogleMap

    lateinit private var userLocationService: UserLocationService
    private lateinit var token: String
    private var survivorMarkers: List<LatLng> = emptyList()
    private var zombieMarkers: List<LatLng> = emptyList()
    private var myMarker: LatLng = LatLng(52.0, 21.0)

    private val serviceConnection = object : android.content.ServiceConnection {
        override fun onServiceDisconnected(p0: ComponentName?) {
        }

        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            if (p1 is UserLocationService.LocalBinder) {
                userLocationService = p1.service
                userLocationService.callback = this@MapsActivity
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        Log.d("zombiaki", "twoja rola to ${this.intent.extras.get("role")}")
        token = this.intent.extras.getString("token")

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        this.applicationContext.bindService(intentFor<UserLocationService>("token" to token), serviceConnection, Context.BIND_AUTO_CREATE)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Warsaw and move the camera
        val warsaw = LatLng(52.2, 21.0)
        mMap.addMarker(MarkerOptions().position(warsaw).title("Marker in Warsaw"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(warsaw))
        mMap.moveCamera(CameraUpdateFactory.zoomTo(12.0f))
    }

    override fun showPositions(survivorPosition: List<LatLng>, zombiePositions: List<LatLng>) {

    }

    override fun onBackPressed() {
        if (token.isNotBlank()) {
            startActivity<LogoutActivity>()
            finish()
        }
        else super.onBackPressed()
    }
}
