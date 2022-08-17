package api

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.core.security.Role
import io.javalin.core.util.RouteOverviewPlugin
import org.unq.ui.bootstrap.getInstagramSystem

enum class InstagramRoles : Role {
    ANYONE, USER
}

fun main(args: Array<String>) {
    InstagramApi().start()
}

class InstagramApi {

    fun start() {
        val instagramSystem = getInstagramSystem()
        val userController = UserController(instagramSystem)


        val app = Javalin.create {
            it.defaultContentType = "application/json"
            it.registerPlugin(RouteOverviewPlugin("/routes"))
            it.accessManager(InstragramAccessManager(instagramSystem))
            it.enableCorsForAllOrigins()
        }

        app.before {
            it.header("Access-Control-Expose-Headers", "*")
        }

        app.routes {
            path("register") {
                post(userController::register, setOf(InstagramRoles.ANYONE))
            }

            path("login") {
                post(userController::login, setOf(InstagramRoles.ANYONE))
            }

            path("user") {
                get(userController::getUser, setOf(InstagramRoles.USER))

                path(":id") {
                    get(userController::getUserWithId, setOf(InstagramRoles.USER))

                    path("follow") {
                        put(userController::follower, setOf(InstagramRoles.USER))
                    }
                }
            }

            path("post") {
                path(":id") {
                    get(userController::getPostWithId, setOf(InstagramRoles.USER))

                    path("comment") {
                        post(userController::addComment, setOf(InstagramRoles.USER))
                    }

                    path("like") {
                        put(userController::likePost, setOf(InstagramRoles.USER))
                    }
                }
            }

            path("search") {
                get(userController::busquedaPorParametro, setOf(InstagramRoles.USER))
            }
        }
        app.start(7000)
    }

}