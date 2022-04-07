package com.park9eon.compose.router

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.attributes.ATarget
import org.jetbrains.compose.web.attributes.target
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.ContentBuilder
import org.w3c.dom.HTMLAnchorElement

@Composable
fun Link(
    href: String,
    target: ATarget? = null,
    replace: Boolean = false,
    back: Boolean = false,
    go: Int = 0,
    content: ContentBuilder<HTMLAnchorElement>? = null,
) {
    val history = getHistory()

    A(
        href = href,
        attrs = {
            if (target != null) {
                target(target)
            }

            onClick {
                // TODO: check local path
                val path = href
                console.log(path, target, it, history)
                if (!(target == null || target == ATarget.Self)) {
                    // pass when has target

                    return@onClick
                }

                if (it.metaKey || it.altKey || it.ctrlKey || it.shiftKey) {
                    // pass when with key

                    return@onClick
                }

                if (history == null) {
                    return@onClick
                }

                it.preventDefault()

                // TODO: move to navigate
                when {
                    replace -> history.replace(path)
                    back -> history.back()
                    go != 0 -> history.go(go)
                    else -> history.push(path)
                }
            }
        },
        content = content,
    )
}
