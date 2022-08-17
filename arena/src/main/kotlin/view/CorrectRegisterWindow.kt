package view

import org.uqbar.arena.kotlin.extensions.caption
import org.uqbar.arena.kotlin.extensions.text
import org.uqbar.arena.kotlin.extensions.thisWindow
import org.uqbar.arena.kotlin.extensions.with
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.Label
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.windows.Dialog
import org.uqbar.arena.windows.WindowOwner
import viewModel.LoginViewModel
import viewModel.RegisterModel

class CorrectRegisterWindow(owner: WindowOwner, model: RegisterModel): Dialog<RegisterModel>(owner,model){
    override fun createFormPanel(p0: Panel?) {
        iconImage="instagram.png"

        Label(p0) with {
            text = "Registrado con exito!"
            fontSize = 15
        }

        Button(p0) with {
            caption = "Cerrar"
            onClick {
                thisWindow.close()

            }
        }
    }
}