package com.park9eon.compose

fun match(pattern: String, path: String): MatchResult? {
    if (pattern != path) {
        return null
    }

    return MatchResult(
        path = path,
    )
}
