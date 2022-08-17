package application

import org.unq.ui.bootstrap.getInstagramSystem
import org.uqbar.arena.Application
import org.uqbar.arena.windows.Window
import viewModel.LoginViewModel
import view.LoginWindow

fun main() {

   InstagramSystemApplication().start()
}

class InstagramSystemApplication(): Application(){

    override fun createMainWindow(): Window<*> {
        val model = LoginViewModel()
        return LoginWindow(this, model)
    }
}