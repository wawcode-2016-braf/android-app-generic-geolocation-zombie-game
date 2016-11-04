package abm.ant8.brafhackaton

import abm.ant8.brafhackaton.game.*
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
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
    lateinit private var token: String
    lateinit var callback: UserLocationServiceCallback

    override fun onBind(p0: Intent?): IBinder {
        token = p0?.extras?.getString("token") ?: ""
        Log.d("zombiaki", "sledzenie!")
        val mapper = jacksonObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
//                setDateFormat()

        if (mGoogleApiClient == null) {
            mGoogleApiClient = GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(object : GoogleApiClient.ConnectionCallbacks {
                        override fun onConnected(p0: Bundle?) {
                            val locationRequest = LocationRequest.create().setFastestInterval(Globals.fastestIntervalInMillis).setInterval(Globals.intervalInMillis)
                            val locationListener = LocationListener {
                                location ->
                                run {
                                    Log.d("zombiaki", "lokalizacja dostana, ${location.latitude}, ${location.longitude}")
                                    callback.getMyPosition(LatLng(location.latitude, location.longitude))
                                    doAsync {
                                        val response = URL("https://immense-wave-80129.herokuapp.com/api/game?token=$token").readText()
                                        val gameWrapper = mapper.readValue<GameWrapper>(response)
                                        Log.d("zombiaki", "$response")
                                        Log.d("zombiaki", "${gameWrapper.info.startDate}")
                                    }
                                }
                            }
                            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, locationListener)
                        }

                        override fun onConnectionSuspended(p0: Int) {
                            Log.d("zombiaki", "zawieszono")
                        }
                    })
                    .addOnConnectionFailedListener { Log.d("zombiaki", "błąd komunikacji z serwerem") }
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

    override fun onDestroy() {
        mGoogleApiClient?.disconnect()
    }
}