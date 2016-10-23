package abm.ant8.brafhackaton

import abm.ant8.brafhackaton.game.UserLocationServiceCallback
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.doAsync
import java.net.URL

class UserLocationService : Service(), AnkoLogger {
    private val mBinder = LocalBinder()
    private var mGoogleApiClient: GoogleApiClient? = null
    private var googleApiConnected: Boolean = false
    lateinit private var token: String
    lateinit var callback: UserLocationServiceCallback

    override fun onBind(p0: Intent?): IBinder {
        token = p0?.extras?.getString("token") ?: ""
        Log.d("dupa", "usluga w ogole startuje onbind, tokien to $token")
        Log.d("dupa", "sledzenie!")
        if (mGoogleApiClient == null) {
            mGoogleApiClient = GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(object : GoogleApiClient.ConnectionCallbacks {
                        override fun onConnected(p0: Bundle?) {
                            val locationRequest = LocationRequest.create().setFastestInterval(Globals.fastestIntervalInMillis).setInterval(Globals.intervalInMillis)
                            val locationListener = LocationListener {
                                location ->
                                run {
                                    Log.d("dupa", "lokalizacja dostana, ${location.latitude}, ${location.longitude}")
                                    callback.getMyPosition(LatLng(location.latitude, location.longitude))
                                    doAsync {
                                        val response = URL("https://immense-wave-80129.herokuapp.com/api/game?token=$token").readText()
                                        Log.d("dupa", "${URL("http://isup.me").readText()}")
                                    }
                                }
                            }
                            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, locationListener)

                            var mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                                    mGoogleApiClient)
//                            ctx.toast(mLastLocation?.getLatitude().toString())
                            Log.d("dupa", mLastLocation?.getLatitude().toString())
//                            ctx.toast(mLastLocation?.getLongitude().toString())
                            Log.d("dupa", mLastLocation?.getLongitude().toString())
                            if (mLastLocation != null) googleApiConnected = true
                        }

                        override fun onConnectionSuspended(p0: Int) {
                            Log.d("dupa", "zawieszono")
                        }
                    })
                    .addOnConnectionFailedListener { Log.d("dupa", "błąd komunikacji z serwerem") }
                    .addApi(LocationServices.API)
                    .build()
        }

        doAsync {
            while(mGoogleApiClient?.isConnected != true) {
                Thread.sleep(500)
                mGoogleApiClient?.connect()
            }
        }

        return mBinder
    }

    inner class LocalBinder : Binder() {
        internal
        val service: UserLocationService
            get() = this@UserLocationService

    }

    fun startTracking() {

    }

    override fun onDestroy() {
        mGoogleApiClient?.disconnect()
    }
}