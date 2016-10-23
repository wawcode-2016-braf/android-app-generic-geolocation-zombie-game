package abm.ant8.brafhackaton

import android.app.IntentService
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import org.jetbrains.anko.doAsync

class UserLocationService : IntentService("UserLocationService") {
    private var mGoogleApiClient: GoogleApiClient? = null
    private var googleApiConnected: Boolean = false

    override fun onHandleIntent(p0: Intent?) {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(object : GoogleApiClient.ConnectionCallbacks {
                        override fun onConnected(p0: Bundle?) {
                            Log.d("dupa", "polaczono")

                            val locationRequest = LocationRequest.create().setFastestInterval(1000).setInterval(10000)
                            val locationListener = LocationListener { Log.d("dupa", "lokalizacja dostana") }
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
                Log.d("dupa", "${mGoogleApiClient?.isConnected} ${mGoogleApiClient?.isConnecting} ")
            }
        }
    }

    override fun onDestroy() {
        mGoogleApiClient?.disconnect()
    }
}