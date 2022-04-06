package com.park9eon.compose.vaadin

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinext.js.require
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.builders.InputAttrsScope
import org.jetbrains.compose.web.dom.TagElement
import org.w3c.dom.HTMLInputElement

@Composable
fun VaadinDateTimePicker() {
    remember { require("@vaadin/date-time-picker") }

    TagElement<HTMLInputElement>("vaadin-date-time-picker", {
        with(InputAttrsScope(InputType.DateTimeLocal, this)) {
            onInput {
                console.log(1)
                console.log(it.value)
            }
            onChange {
                console.log(2)
                console.log(it.value)
            }
        }
    }) {

    }
}
