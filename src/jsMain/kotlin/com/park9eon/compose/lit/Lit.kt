package com.park9eon.compose.lit

import org.w3c.dom.HTMLElement
import typescript.TemplateExpression
import typescript.TemplateSpan

/**
 * https://lit.dev/docs/
 */
@JsModule("lit")
@JsNonModule
external object Lit {

    interface TemplateResult

    interface RootPart

    val nothing: dynamic

    fun html(strings: Array<String>, values: Array<dynamic> = definedExternally): TemplateResult

    fun render(value: dynamic, container: HTMLElement, options: dynamic = definedExternally): RootPart

}

@JsModule("lit/directives/guard.js")
@JsNonModule
external object Guard {

    fun <T> guard(value: dynamic, func: dynamic): T

}
