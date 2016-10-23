package abm.ant8.brafhackaton

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import org.jetbrains.anko.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        linearLayout {
            val playerNameView = editText {
                hint = "Twój nick"
            }
            button("zaloguj") {
                onClick {
                    startActivity<MapsActivity>("token" to "123", "role" to "czlowiek", "name" to playerNameView.text.toString())
                }
            }
        }

    }
}
