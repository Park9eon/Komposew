package com.park9eon.compose

data class PathPatternResult(
    val path: String,
    val pathname: String,
    val search: String,
    val params: Map<String, String?>,
    val hash: String,
)
