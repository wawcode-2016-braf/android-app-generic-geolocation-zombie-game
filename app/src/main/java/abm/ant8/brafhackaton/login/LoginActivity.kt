package abm.ant8.brafhackaton.login

import abm.ant8.brafhackaton.MapsActivity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.jetbrains.anko.*
import java.net.URL

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mapper = jacksonObjectMapper()

        verticalLayout {
            val playerNameView = editText {
                hint = "Twój nick"
            }
            button("zaloguj") {
                onClick {
                    doAsync {
                        val response = URL("https://immense-wave-80129.herokuapp.com/api/token/${playerNameView.text}").readText()
                        val loginData: LoginData = mapper.readValue(response)
                        uiThread {
                            startActivity<MapsActivity>("token" to loginData.data.token, "role" to loginData.data.role, "name" to loginData.data.name, "id" to loginData.data.id)
                        }
                    }
                }
            }
        }

    }
}
