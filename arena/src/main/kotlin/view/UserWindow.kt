package view

import org.uqbar.arena.kotlin.extensions.*
import org.uqbar.arena.widgets.Button
import org.uqbar.arena.widgets.Label
import org.uqbar.arena.widgets.Panel
import org.uqbar.arena.widgets.TextBox
import org.uqbar.arena.windows.SimpleWindow
import org.uqbar.arena.windows.WindowOwner
import org.uqbar.commons.model.exceptions.UserException
import viewModel.DraftPostViewModel
import viewModel.EditProfileViewModel
import viewModel.PostViewModel
import viewModel.UserViewModel

class UserWindow (owner: WindowOwner, model: UserViewModel): SimpleWindow<UserViewModel>(owner,model) {

    override fun addActions(panel: Panel) {
        Button(panel) with {
            caption = "Add new Post"
            onClick {
                val modelo = DraftPostViewModel(modelObject.portrait,modelObject.landscape,modelObject.description)
                val view = AddPostWindow(thisWindow,modelo)
                view.onAccept {
                        modelObject.agregarPost(modelo)
                }
                view.open()
            }
        }

        Button(panel) with {
            caption = "Edit Post"
            bindEnabledTo("check")
            onClick {
                if (modelObject.selected == null) {
                    throw UserException("Selecione un post")
                }
                val modelo = DraftPostViewModel(modelObject.selected!!.portrait,modelObject.selected!!.landScape,modelObject.selected!!.description)
                val view = EditPostWindow(thisWindow,modelo)
                view.onAccept {
                    modelObject.editarPost(modelObject.selected!!.ide, modelo)
                }
                view.open()
            }
        }


        Button(panel) with {
            caption = "Delete Post"
            bindEnabledTo("check")
            onClick {
                if (modelObject.selected == null) {
                    throw UserException("Selecione un post")
                }
                val view = DeletePostWindow(thisWindow,modelObject.selected!!)
                view.onAccept {
                    modelObject.eliminarPost(modelObject.selected!!.ide)
                }
                view.open()
            }
        }
    }

    fun panelTextButton(panel: Panel, textLabel: String, widthLabel1: Int, modelObject: UserViewModel){
        Panel(panel) with {
            asHorizontal()
            Label(it) with {
                text = textLabel
                width = widthLabel1
                alignLeft()
            }
            TextBox(it) with {
                bindTo("busqueda")
                width=300
                height=15
            }
            Button(it)with {caption="search"
            onClick { modelObject.buscarPorDescripcion() }
            }
        }
    }

    fun labelwithLabel(panel:Panel,textLabel:String,widthLabel1:Int,widthLabel2: Int,bind:String) {
        Panel(panel) with {
            asHorizontal()
            Label(it) with {
                text = textLabel
                width = widthLabel1
                alignLeft()
            }
            Label(it) with {
                width = widthLabel2
                bindTo(bind)
            }
        }
    }


    override fun createFormPanel(panel: Panel) {
        title = "User View"
        iconImage="instagram.png"

        labelwithLabel(panel, "Id : ", 18, 35, "id")
        labelwithLabel(panel, "Name : ", 40, 40, "name")
        labelwithLabel(panel, "Email : ", 45, 110, "email")

        Button(panel) with {
            caption = "Edit Profile"
            setMinWidth(60)
            onClick {
                val modelo = EditProfileViewModel(modelObject.name, modelObject.password, modelObject.image)
                val view = EditProfileWindow(thisWindow,modelo)
                view.onAccept {
                    editProfile(modelo)
                }
                view.open()
            }
        }

        Label(panel) withText "-------------------------------------------------------------------------------------------------------------------------------------------------"
        panelTextButton(panel,"Search",40,modelObject)
            table<PostViewModel>(panel){
                setMinWidth(500)
                setMinHeight(400)
                bindItemsTo("post")
                bindSelectionTo("selected")
                visibleRows = 10
                column {
                    title = "#"
                    fixedSize = 50
                    bindContentsTo("ide")
                }
                column {
                    title = "Descripcion"
                    fixedSize = 70
                    bindContentsTo("description")
                }
                column {
                    title = "Landscape"
                    fixedSize = 130
                    bindContentsTo("landScape")
                    }
                column {
                    title = "Portrait"
                    fixedSize = 130
                    bindContentsTo("portrait")
                }
            }
    }

    fun editProfile(model: EditProfileViewModel) {
        modelObject.editarProfile(model)
    }
}

