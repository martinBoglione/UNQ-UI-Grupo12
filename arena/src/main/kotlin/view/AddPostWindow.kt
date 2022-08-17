package view

import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.Label
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.widgets.TextBox
import org.uqbar.arena.windows.Dialog
import org.uqbar.arena.windows.WindowOwner
import viewModel.DraftPostViewModel

class AddPostWindow(owner:WindowOwner, model : DraftPostViewModel ): Dialog <DraftPostViewModel> (owner,model) {

    override fun createFormPanel(mainPanel: Panel) {
        iconImage="instagram.png"

        Label(mainPanel) with{
            alignLeft()
            text="Portrait"
        }

        TextBox(mainPanel) with {
            width = 200
            bindTo("portrait")
        }

        Label(mainPanel) with{
            alignLeft()
            text="Landscape"
        }

        TextBox(mainPanel) with {
            bindTo("landscape")
        }

        Label(mainPanel) with{
            alignLeft()
            text="Description"
        }

        TextBox(mainPanel) with {
            bindTo("description")
        }
    }

    override fun addActions(mainPanel: Panel) {

        Button(mainPanel) with {
            caption = "Accept"
            onClick {
                if (modelObject.portrait.isEmpty()) {
                    showError("Se necesita un retrato")
                } else {
                    accept()
                }
            }
        }

        Button(mainPanel) with {
            caption = "Cancel"
            onClick { cancel() }
        }
    }
}



