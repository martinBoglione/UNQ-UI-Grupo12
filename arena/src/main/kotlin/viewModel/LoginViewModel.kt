package viewModel

import org.unq.ui.bootstrap.getInstagramSystem
import org.unq.ui.model.InstagramSystem
import org.uqbar.commons.model.annotations.Observable

@Observable
class LoginViewModel() {
    var email:String=""
    var password:String=""
    var instagram: InstagramSystem= getInstagramSystem()

    fun login(): UserViewModel {
        val user = instagram.login(email,password)
        return UserViewModel(instagram,user.id,user.email,user.name,user.password,user.image)
    }

}

