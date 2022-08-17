package view

import org.unq.ui.model.UsedEmail
import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.*
import org.uqbar.arena.windows.SimpleWindow
import org.uqbar.arena.windows.WindowOwner
import viewModel.LoginViewModel
import viewModel.RegisterModel
import java.awt.Color

class RegisterWindow(owner: WindowOwner, model: RegisterModel) : SimpleWindow<RegisterModel>(owner,model) {

    override fun addActions(p0: Panel?) {}

    override fun createFormPanel(mainPanel: Panel) {
        title = "Registro Instagram"
        iconImage = "instagram.png"

        Label(mainPanel) with {
            text = "Ingrese Nombre y Apellido"
            alignLeft()
        }

        Label(mainPanel) with {
            text = "Por ejemplo : Juan Perez"
            fontSize = 7
            alignLeft()
        }

        TextBox(mainPanel) with { setWidth(200) bindTo ("nyap") }

        Label(mainPanel) withText ""
        Label(mainPanel) with {
            text = "Ingrese el email"
            alignLeft()
        }

        Label(mainPanel) with {
            text = "Por ejemplo : arena@gmail.com"
            fontSize = 7
            alignLeft()
        }

        TextBox(mainPanel) with { setWidth(200) bindTo ("email") }

        Label(mainPanel) withText ""
        Label(mainPanel) with {
            text = "Ingrese su contraseña"
            alignLeft()
        }

        PasswordField(mainPanel) with { setWidth(200) bindTo ("password") }

        Label(mainPanel) withText ""
        Button(mainPanel) with {
            text = "Agregar imagen"
            onClick { InsertImageWindow(thisWindow, modelObject).open() }
        }

        Label(mainPanel) withText ""
        Label(mainPanel) withText "Al registrarte aceptas terminos y condiciones de uso"

        Button(mainPanel) with {
            caption = "Registrarse"
            onClick {
                if(modelObject.completoTodosLosCampos()){
                registrarSiEmailNoRep()
            }
          }
        }

        Link(mainPanel) with {
            text = "¿Ya tienes cuenta? Logueate!"
            onClick {
                thisWindow.close()
            }
        }
    }


    fun registrarSiEmailNoRep(){
        try {
            modelObject.register()
            CorrectRegisterWindow(thisWindow, modelObject).open()
        } catch (e: UsedEmail) {
            showInfo("El email ya existe")
        }
    }
}



