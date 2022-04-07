package com.park9eon.compose.vaadin

import androidx.compose.runtime.*
import kotlinext.js.require
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.renderComposable
import org.w3c.dom.HTMLElement

abstract external class VaadinDialogElement : HTMLElement {
    var renderer: (root: HTMLElement) -> Unit
}

@Composable
fun VaadinDialog(
    opened: Boolean,
) {
    remember { require("@vaadin/dialog") }

    TagElement<VaadinDialogElement>("vaadin-dialog", {
        ref {
            it.renderer = { root: HTMLElement ->
                renderComposable(root) {
                    Div {
                        var value by remember { mutableStateOf(0) }
                        P {
                            Text("$value")
                        }
                        Button(attrs = {
                            onClick {
                                value += 1
                            }
                        }) {
                            Text("Click Me!")
                        }
                    }
                }
            }

            onDispose {
            }
        }
        attr("opened", "$opened")
        onClick {
            console.log("Clicked!!")
        }
    }) {
        Text("This is modal")
    }
}
