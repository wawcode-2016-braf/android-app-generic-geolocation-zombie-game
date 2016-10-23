package abm.ant8.brafhackaton.login

import abm.ant8.brafhackaton.MapsActivity
import abm.ant8.brafhackaton.R
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.jetbrains.anko.*
import java.net.URL

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mapper = jacksonObjectMapper()

        val loginBackgroundImage: Drawable
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            loginBackgroundImage = ctx.resources.getDrawable(R.drawable.androidtlologin, ctx.theme)
        } else {
            loginBackgroundImage = ctx.resources.getDrawable(R.drawable.androidtlologin)
        }

        verticalLayout {
            verticalLayout {
                lparams(height = dip(0), width = matchParent, weight = 1.0f)
                imageView { image = loginBackgroundImage }
            }
            verticalLayout {
                lparams(height = dip(0), width = matchParent, weight = 1.0f)
                val playerNameView = editText {
                    hint = "Twój nick"
                    textColor = ContextCompat.getColor(ctx, R.color.textColor)
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
                        finish()
                    }
                    textColor = ContextCompat.getColor(ctx, R.color.textColor)
                    backgroundColor = ContextCompat.getColor(ctx, R.color.buttonColour)
                }
            }
            backgroundColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
        }

    }
}
