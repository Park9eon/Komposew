package com.park9eon.compose.vaadin

import androidx.compose.runtime.*
import kotlinext.js.require
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.renderComposable
import org.w3c.dom.HTMLElement

abstract external class VaadinDialogElement : HTMLElement {
    operator fun set(s: String, value: (dynamic) -> Unit)
    var opened: Boolean
    var renderer: (root: HTMLElement) -> Unit
}

@Composable
fun VaadinDialog(
    opened: Boolean,
    onOpened: (opened: Boolean) -> Unit,
    theme: String? = null,
    ariaLabel: String? = null,
    resizable: Boolean = false,
    draggable: Boolean = false,
    modeless: Boolean = false,
    content: @Composable () -> Unit,
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
            element.addEventListener("opened-changed", { event ->
                val dialog = event.target.unsafeCast<VaadinDialogElement>()

                onOpened(dialog.opened)
            })

            onDispose {
                composition?.dispose()
                composition = null
            }
        }

        if (opened) {
            attr("opened", "")
        }

        if (theme != null) {
            attr("theme", theme)
        }

        if (draggable) {
            attr("draggable", "")
        }

        if (ariaLabel != null) {
            attr("aria-label", ariaLabel)
        }

        if (resizable) {
            attr("resizeable", "")
        }

        if (modeless) {
            attr("modeless", "")
        }
    }, null)
}
