package abm.ant8.brafhackaton.login

import abm.ant8.brafhackaton.R
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import org.jetbrains.anko.*

class LogoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                button("wyloguj się") {
                    onClick {
                        startActivity<LoginActivity>()
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
