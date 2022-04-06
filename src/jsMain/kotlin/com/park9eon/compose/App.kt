package com.park9eon.compose

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposableInBody

@Composable
fun Navigation() {
    Div {
        Link("/user") {
            Text("/user")
        }
        Link("/user/kim") {
            Text("/user/kim")
        }
        Link("/about") {
            Text("/user")
        }
        Link("/") {
            Text("/home")
        }
    }
}

fun main() {
    val message = "Hello, Kotlin World!"

    console.log(message)

    renderComposableInBody {
        Router {
            Route(path = "/user") {
                Navigation()
                Div {
                    Text("User")
                }
            }
            Route(path = "/user/:username") {
                val params = it.params

                Navigation()
                Div {
                    Text("User : ${params["username"]}")
                }
            }
            Route(path = "/about") {
                Navigation()

                Div {
                    Text("About")
                }
            }
            Route(path = "/") {
                Navigation()

                Div {
                    Text("Hello")
                }
            }
        }
    }
}
