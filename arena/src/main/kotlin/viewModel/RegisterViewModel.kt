package viewModel

import org.unq.ui.model.InstagramSystem
import org.uqbar.commons.model.annotations.Observable

@Observable
class RegisterModel(var instagram: InstagramSystem) {

    var nyap=""
    var email=""
    var password=""
    var image="http://www.defaultImage.com"
    set(value) {
        if(image.isEmpty()){
            image="http://www.defaultImage.com"
        }
    }

    fun completoTodosLosCampos(): Boolean {
        return (email.isNotEmpty() && password.isNotEmpty() &&nyap.isNotEmpty() && terminaEnDominioAceptado())
    }

    fun register() {
        instagram.register(nyap,email,password,image)
    }

    fun terminaEnDominioAceptado(): Boolean {
        return email.endsWith("@gmail.com") ||email.endsWith("@hotmail.com")
                || email.endsWith("@yahoo.com") ||email.endsWith("@outlock.com")
    }

}