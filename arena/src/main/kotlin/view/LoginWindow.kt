package view

import org.unq.ui.model.NotFound
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.*
import org.uqbar.arena.windows.SimpleWindow
import org.uqbar.arena.windows.WindowOwner
import org.uqbar.commons.model.exceptions.UserException
import viewModel.LoginViewModel
import viewModel.RegisterModel

class LoginWindow(owner: WindowOwner,model: LoginViewModel): SimpleWindow<LoginViewModel>(owner,model) {

    override fun createFormPanel(mainPanel: Panel) {

        title = "InstagramSystem - Login"
        iconImage = "instagram.png"


        Label(mainPanel) with {
            alignLeft()
            text = "Email"
        }

        TextBox(mainPanel) with {
            width = 300
            bindTo("email")
        }

        Label(mainPanel) with {
            alignLeft()
            text = "Password"
        }

        PasswordField(mainPanel) with {
            bindTo("password")
        }

        Button(mainPanel) with{
            caption = "Login"
            onClick {
                try {
                    val userModel = modelObject.login()
                    thisWindow.close()
                    UserWindow(thisWindow,userModel).open()
                }
                catch(e:NotFound) {
                    throw UserException("Usuario y/o contraseña incorrecta")
                }
            }
        }
    }

    override fun addActions(p0: Panel) {
        Link(p0) with { text="¿No tienes cuenta? Registrate!"
        onClick {
            var registro= RegisterModel(modelObject.instagram)
           // thisWindow.close()
            RegisterWindow(thisWindow,registro).open()

            }
        }
    }
}