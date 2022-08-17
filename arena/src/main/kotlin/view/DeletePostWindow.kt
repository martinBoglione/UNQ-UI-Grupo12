package view

import org.uqbar.arena.kotlin.extensions.asHorizontal
import org.uqbar.arena.kotlin.extensions.caption
import org.uqbar.arena.kotlin.extensions.text
import org.uqbar.arena.kotlin.extensions.with
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.Label
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.windows.Dialog
import org.uqbar.arena.windows.WindowOwner
import viewModel.PostViewModel

class DeletePostWindow(owner:WindowOwner, model : PostViewModel): Dialog<PostViewModel>(owner,model) {

    override fun createFormPanel(mainPanel: Panel) {
        iconImage="instagram.png"

        Label(mainPanel) with  {
            text= "Do you remove ${modelObject.ide}?"
        }
    }

    override fun addActions(mainPanel: Panel) {
        Button(mainPanel) with {
            caption= "Accept"
            onClick { accept() }
        }

        Button(mainPanel) with {
            caption = "Cancel"
            onClick { cancel() }
        }
    }
}