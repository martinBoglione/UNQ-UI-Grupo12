package api

import io.javalin.core.security.AccessManager
import io.javalin.core.security.Role
import io.javalin.http.Context
import io.javalin.http.Handler
import io.javalin.http.UnauthorizedResponse
import org.unq.ui.model.InstagramSystem
import org.unq.ui.model.NotFound
import token.NotValidToken
import token.TokenUserController

class InstragramAccessManager(val instagramSystem: InstagramSystem) : AccessManager {

    val tokenController = TokenUserController()

    override fun manage(handler: Handler, ctx: Context, roles: MutableSet<Role>) {
        val token = ctx.header("Authorization")
        when {
            roles.contains(InstagramRoles.ANYONE) -> handler.handle(ctx)
            token === null -> throw  UnauthorizedResponse()
            roles.contains(InstagramRoles.USER) -> {
                try {
                    val userId = tokenController.validateToken(token)
                    instagramSystem.getUser(userId)
                    ctx.attribute("userId", userId)
                    handler.handle(ctx)
                } catch (e: NotValidToken) {
                    throw UnauthorizedResponse("Not valid token")
                } catch (e: NotFound) {
                    throw UnauthorizedResponse("Not valid token")
                }

            }
        }
    }

}
