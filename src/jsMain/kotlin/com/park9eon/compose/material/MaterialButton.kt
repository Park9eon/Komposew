package com.park9eon.compose.material

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.jetbrains.compose.web.dom.TagElement
import org.w3c.dom.HTMLButtonElement

@Composable
fun MaterialButton() {
    remember { kotlinext.js.require("@material/mwc-button") }

    TagElement<HTMLButtonElement>("mwc-button", {
        attr("label", "Click Me!")
        attr("raised", "")
    }) {

    }
}
