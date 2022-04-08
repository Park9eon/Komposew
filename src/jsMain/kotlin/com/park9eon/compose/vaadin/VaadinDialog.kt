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
    content: @Composable () -> Unit
) {
    remember { require("@vaadin/dialog") }
    var composition: Composition? by remember { mutableStateOf(null) }

    TagElement<VaadinDialogElement>("vaadin-dialog", {
        ref { element ->
            element.renderer = renderer@{ root ->
                if (composition != null) {
                    return@renderer
                }

                composition = renderComposable(root) {
                    content()
                }
            }

            onDispose {
                composition?.dispose()
                composition = null
            }
        }
        attr("opened", "$opened")
    }, null)
}
