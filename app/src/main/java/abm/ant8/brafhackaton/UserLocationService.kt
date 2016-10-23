package abm.ant8.brafhackaton

import android.app.IntentService
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import org.jetbrains.anko.ctx
import org.jetbrains.anko.toast

class UserLocationService : IntentService("UserLocationService") {
//    private var mGoogleApiClient: GoogleApiClient? = null
//    private var googleApiConnected: Boolean = false

    override fun onHandleIntent(p0: Intent?) {
//        Log.d("dupa", "${mGoogleApiClient == null}")
//        if (mGoogleApiClient == null) {
//            mGoogleApiClient = GoogleApiClient.Builder(this)
//                    .addConnectionCallbacks(object : GoogleApiClient.ConnectionCallbacks {
//                        override fun onConnected(p0: Bundle?) {
//                            Log.d("dupa", "polaczono")
//                            var mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
//                                    mGoogleApiClient)
////                            ctx.toast(mLastLocation?.getLatitude().toString())
//                            Log.d("dupa", mLastLocation?.getLatitude().toString())
////                            ctx.toast(mLastLocation?.getLongitude().toString())
//                            Log.d("dupa", mLastLocation?.getLongitude().toString())
//                            if (mLastLocation != null) googleApiConnected = true
//                        }
//                        override fun onConnectionSuspended(p0: Int) {
//                            Log.d("dupa", "zawieszono")
//                        }
//                    })
//                    .addOnConnectionFailedListener { toast("błąd komunikacji z serwerem") }
//                    .addApi(LocationServices.API)
//                    .build()
//        }
//        mGoogleApiClient?.connect()
//
//        Log.d("dupa", "${mGoogleApiClient?.isConnected} ${mGoogleApiClient?.isConnecting} ")
        Log.d("dupa", "handle intent")
    }

    override fun onDestroy() {
        Log.d("dupa", "usluga zatrzymana")
//        mGoogleApiClient?.disconnect()
//        Log.d("dupa", "${mGoogleApiClient?.isConnected} ${mGoogleApiClient?.isConnecting} ")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("dupa", "usluga wystartowana - on start command")
        return super.onStartCommand(intent, flags, startId)
    }
}