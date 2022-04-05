package com.park9eon.compose

import org.jetbrains.compose.web.renderComposableInBody

fun main() {
    val message = "Hello, Kotlin World!"

    console.log(message)

    renderComposableInBody {
    }
}
