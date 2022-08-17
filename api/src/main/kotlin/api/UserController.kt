package api

import io.javalin.http.BadRequestResponse
import io.javalin.http.Context
import org.unq.ui.model.*
import token.TokenUserController


class UserController(val instagramSystem: InstagramSystem) {

    val tokenController = TokenUserController()

    private fun getUserId(ctx: Context): String {
        return ctx.attribute<String>("userId") ?: throw BadRequestResponse("Not found user")
    }

    fun login(ctx: Context) {
        val dataLogin = ctx.body<LoginData>()
        try {
            val user = instagramSystem.login(dataLogin.email, dataLogin.password)
            ctx.header("Authorization", tokenController.generateToken(user))
            ctx.json(SimpleUserID(user.id,user.name,user.image))
            ctx.status(200)
        } catch (e: NotFound) {
            ctx.status(404)
            ctx.json(UserNotFound("Error", "User not found"))
        }
    }

    fun register(ctx: Context) {
        val dataRegister = ctx.body<RegisterData>()
        try {
            /** Issue #3 */
            if(dataRegister.email.isNotBlank() && dataRegister.email.contains("@gmail.com")) {
            val user = instagramSystem.register(dataRegister.name, dataRegister.email, dataRegister.password, dataRegister.image)
            ctx.header("Authorization", tokenController.generateToken(user))
            ctx.status(201)
            ctx.json(SimpleUserID(user.id,user.name,user.image))
            } else {
                ctx.status(400)
                ctx.json(ErrorResponse("El email debe ser valido"))
            }
        } catch (e: UsedEmail) {
            ctx.status(409)
            ctx.json(EmailNotValid("Email already registered"))
        }
    }


    fun getUser(ctx: Context) {
        val id = getUserId(ctx)
        val user = instagramSystem.getUser(id)
        val timeline = instagramSystem.timeline(id).map {
            SimpleTimeLine(it.id, it.description, it.portrait, it.landscape, hacerSimpleLikes(it.likes),
                    FormatTime().format(it.date), hacerSimpleUserID(it.user))
        }
        ctx.json(UserConTimelineYFollowers(user.name, user.image, hacerSimpleFollowers(user.followers),  timeline))
        ctx.status(200)
    }


    fun getUserWithId(ctx: Context) {
        val userId = ctx.pathParam("id")
        val user = instagramSystem.getUser(userId)
        try {
            val followers = hacerSimpleFollowers(user.followers)
            val posts = hacerPostUser(instagramSystem.searchByUserId(userId))
            ctx.json(DraftUser(user.id,user.name, user.image, followers, posts))
        } catch (e: NotFound) {
            ctx.status(404).json(ErrorResponse("No existe user con ese id = $userId"))
        }
    }

    fun getPostWithId(ctx: Context) {
        val postId = ctx.pathParam("id")
        val post = instagramSystem.getPost(postId)
        try {
            val simpleUser = hacerSimpleUserID(post.user)
            val simpleLikes = hacerSimpleLikes(post.likes)
            val simpleComment = hacerSimpleCommentsID(post.comments, simpleUser)
            ctx.json(SimplePostID(post.id, post.description, post.portrait, post.landscape, simpleLikes,
                    FormatTime().format(post.date), simpleUser, simpleComment))
        } catch (e: NotFound) {
            ctx.status(404)
            ctx.json(ErrorResponse("Not found post with id: $postId"))
        }
    }

    fun addComment(ctx: Context) {
        val postId = ctx.pathParam("id")
        val draftComment = ctx.body<DraftComment>()
        try {
            /** Issue #5 */
            if(draftComment.component1().isNotBlank()) {
                instagramSystem.addComment(postId, getUserId(ctx), draftComment)
                ctx.status(200)
                ctx.json(OkResponse("ok"))
            } else {
                ctx.status(400)
                ctx.json(ErrorResponse("No se puede hacer comentarios en blanco"))
            }

        } catch (e: NotFound) {
            ctx.status(404)
            ctx.json(ErrorResponse("Not found post with id $postId"))
        }
    }

    fun follower(ctx: Context) {

        val userId = getUserId(ctx)
        val followId = ctx.pathParam("id")
        try {
            /** Issue #2 y #4 */
            if(userId == followId) {
                ctx.status(400)
                ctx.json(ErrorResponse("El usuario no puede seguirse a si mismo"))
            } else {
                instagramSystem.updateFollower(userId,followId)
                ctx.status(200)
                ctx.json(OkResponse("ok"))

            }
        } catch (e: NotFound) {
            ctx.status(404)
            ctx.json(ErrorResponse("Not found user with id $followId"))
        }
    }

    fun likePost(ctx: Context) {

        val userId = getUserId(ctx)
        val postId = ctx.pathParam("id")
        try {
            instagramSystem.updateLike(postId, userId)
            ctx.status(200)
            ctx.json(OkResponse("ok"))
        } catch (e: NotFound) {
            ctx.status(404)
            ctx.json(ErrorResponse("Not found post with id $postId"))
        }

    }

    fun busquedaPorParametro(ctx: Context) {

        val parametro = ctx.queryParam("q")  ?: throw  BadRequestResponse("Search vacio")
            try {
                var postContent = hacerPostContent(instagramSystem.searchByTag(parametro!!))
                ctx.status(200)
                ctx.json(PostByTag(postContent))
            } catch (e: NotATag) {
                val userContent = hacerUserContent(instagramSystem.searchByName(parametro!!))
                ctx.status(200)
                ctx.json(UserByName(userContent))
            }
    }


    fun hacerSimpleLikes(likes: MutableList<User>): List<SimpleUserID> {
        return likes.map { hacerSimpleUserID(it) }
    }

    fun hacerSimpleFollowers(followers: MutableList<User>): List<SimpleUserID> {
        return followers.map { hacerSimpleUserID(it) }
    }


    fun hacerSimpleCommentsID(comments: MutableList<Comment>, simpleUser: SimpleUserID): List<SimpleCommentID> {
        return comments.map { SimpleCommentID(it.id, it.body, hacerSimpleUserID(it.user)) }
    }

    fun hacerSimpleUser(user: User): SimpleUser {
        return SimpleUser(user.name, user.image)
    }

    fun hacerSimpleUserID(user: User): SimpleUserID {
        return SimpleUserID(user.id,user.name, user.image)
    }

    fun hacerPostUser(posts:List<Post>): List<PostUser> {
     return posts.map{ PostUser(it.id, it.description, it.portrait, it.landscape, hacerSimpleLikes(it.likes),
         FormatTime().format(it.date),
            hacerSimpleUser(it.user))}
    }

    fun hacerPostContent(postTag:List<Post>): List<PostContent> {
        return postTag.map { PostContent(it.id,it.description,it.portrait,it.landscape,hacerSimpleLikes(it.likes),
            FormatTime().format(it.date),hacerSimpleUser(it.user)) }
    }

    fun hacerUserContent(user:List<User>): List<UserContent> {
        return user.map { UserContent(it.id,it.name,it.image,hacerSimpleFollowers(it.followers))}
    }

}
