package com.park9eon.compose

data class URLPatternResult(
    val path: String,
    val pathname: String,
    val search: String,
    val params: Map<String, String?>,
    val hash: String,
) {

    operator fun get(key: String): String? {
        return this.params[key]
    }

}
