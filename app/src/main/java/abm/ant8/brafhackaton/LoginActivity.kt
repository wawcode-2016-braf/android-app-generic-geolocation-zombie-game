package abm.ant8.brafhackaton

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.jetbrains.anko.*
import java.net.URL

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        verticalLayout {
            val playerNameView = editText {
                hint = "Twój nick"
            }
            button("zaloguj") {
                onClick {
                    doAsync {
                        val response = URL("https://immense-wave-80129.herokuapp.com/api/token/${playerNameView.text.toString()}").readText()
                        Log.d("dupa", response)
                        uiThread {
                            Log.d("dupa", "startuje!")
                            startActivity<MapsActivity>("token" to "123", "role" to "czlowiek", "name" to playerNameView.text.toString())
                        }
                    }
                }
            }
        }

    }
}
