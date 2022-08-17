package viewModel

import org.unq.ui.model.DraftPost
import org.unq.ui.model.InstagramSystem
import org.uqbar.commons.model.annotations.Observable

@Observable
class UserViewModel(val instagram: InstagramSystem, var id: String, var email:String, var name:String, var password: String,var image: String) {

    var portrait:String=""
    var landscape:String=""
    var description:String=""
    lateinit var post:List<PostViewModel>

    init {
        updatePost()
    }

    var busqueda = ""
    set(value) {
    if (value.isEmpty()) {
        updatePost()}
            field=value }

    var selected: PostViewModel? = null

    set(value) {
        check=true
        field=value
    }

    var check= false

    fun buscarPorDescripcion() {
        post = post.filter { it.description.contains(busqueda) }
    }

    fun editarProfile(model: EditProfileViewModel) {
        instagram.editProfile(id,model.name,model.password,model.image)
        this.name = model.name
        this.password = model.password
        this.image = model.image
    }

    fun agregarPost(draftPost: DraftPostViewModel) {
        instagram.addPost(id,DraftPost(draftPost.portrait,draftPost.landscape,draftPost.description))
        updatePost()
    }

    fun updatePost(){
        var userPost= instagram.searchByUserId(id)
        post = userPost.map { post ->PostViewModel(post.id,post.description,post.landscape,post.portrait) }

    }

    fun eliminarPost(postId:String){
        instagram.deletePost(postId)
        updatePost()
    }

    fun editarPost(postId: String, draft: DraftPostViewModel) {
        instagram.editPost(postId, DraftPost(draft.portrait, draft.landscape, draft.description))
        updatePost()
    }
}
