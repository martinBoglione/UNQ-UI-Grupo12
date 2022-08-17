package view

import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.*
import org.uqbar.arena.windows.Dialog

import org.uqbar.arena.windows.WindowOwner
import viewModel.EditProfileViewModel
import viewModel.UserViewModel

class EditProfileWindow(owner: WindowOwner, model: EditProfileViewModel): Dialog<EditProfileViewModel>(owner, model) {

    override fun addActions(p0: Panel?) {
        Button(p0) with {
            caption = "Accept"
            onClick {
                accept()
            }
        }

        Button(p0) with {
            caption = "Cancel"
            onClick {
                cancel()
            }
        }
    }

    override fun createFormPanel(p0: Panel  ?) {
        title = "Edit Profile"
        iconImage="instagram.png"

        Label(p0) with {
            alignLeft()
            text = "Name"
        }

        TextBox(p0) with {
            bindTo("name")
        }

        Label(p0) with {
            alignLeft()
            text = "Password"
        }

        TextBox(p0) with {
            bindTo("password")
        }

        Label(p0) with {
            alignLeft()
            text = "Image"
        }

        TextBox(p0) with {
            bindTo("image")
        }
    }
}