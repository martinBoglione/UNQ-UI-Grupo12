package view

import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.Label
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.widgets.TextBox
import org.uqbar.arena.windows.Dialog
import org.uqbar.arena.windows.WindowOwner
import viewModel.LoginViewModel
import viewModel.RegisterModel

class InsertImageWindow(owner: WindowOwner,model:RegisterModel):Dialog<RegisterModel>(owner, model) {
    override fun createFormPanel(p0: Panel?) {
        iconImage="instagram.png"

        Label(p0) withText "Ingrese una Imagen"
        TextBox(p0) with {bindTo("image") }
        Button(p0)with {
           caption="Aceptar"
            onClick { thisWindow.close()
            }
        }
    }
}