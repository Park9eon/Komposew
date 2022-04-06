package com.park9eon.compose

fun <K, V> Map<K, V>.toSearch(): String {
    if (this.isEmpty()) {
        return ""
    }

    return this.map { (key, value) ->
        "${key}=${value ?: ""}"
    }.joinToString(
        separator = "&",
        prefix = "?",
    )
}

internal fun String.takeNotBlankWithPrefix(prefix: String): String {
    return this.takeIf {
        it.isNotBlank()
    }?.let {
        "$prefix$it"
    }?: ""
}

fun urlPatternMatch(pattern: String, path: String): URLPatternResult? {
    if (path.isBlank()) {
        return null
    }

    val pathWithHash = path.split("#")
    val pathWithoutHash = pathWithHash[0]
    val hash = pathWithHash.getOrNull(1) ?: ""
    val pathWithSearch = pathWithoutHash.split("?")
    val pathname = pathWithSearch[0]
    val search = pathWithSearch.getOrNull(1) ?: ""
    val paramNames = mutableListOf<String>()

    val regex = pattern.trim( // trim
    ).replace( // remove trailing slash
        Regex("/$"),
        "",
    ).let { // escape
        Regex.escape(it)
    }.replace( // parse path params
        Regex(":(\\w+)"),
    ) {
        val paramName = it.groupValues[1]
        paramNames.add(paramName)

        "([^\\/]+)"
    }.let {
        println(it)
        Regex("^${it}/?$")
    }

    if (!regex.matches(pathname)) {
        return null
    }

    // ignore input pathname
    val regexGroupValues = regex.find(
        pathname,
    )?.groupValues?.let{
        it.subList(1, it.size)
    }
    val params = mutableMapOf<String, String?>()

    regexGroupValues?.forEachIndexed { index, value ->
        val key = paramNames[index]

        params[key] = value
    }

    if (search.isNotBlank()) {
        search.split(
            "&",
        ).forEach { it ->
            val keyValue = it.split("=")
            val key = keyValue.getOrNull(0)
                ?: return@forEach
            val value = keyValue.getOrNull(1)
                ?.takeIf { it.isNotBlank() }

            params[key] = value
        }
    }

    return URLPatternResult(
        path = path,
        pathname = pathname,
        search = search.takeNotBlankWithPrefix("?"),
        params = params,
        hash = hash.takeNotBlankWithPrefix("#"),
    )
}
