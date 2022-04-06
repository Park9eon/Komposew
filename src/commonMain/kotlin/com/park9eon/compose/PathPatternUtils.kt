package com.park9eon.compose

object PathPatternUtils {
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

    operator fun PathPatternResult.get(key: String): String? {
        return this.params[key]
    }

    internal fun String.takeNotBlankWithPrefix(prefix: String): String {
        return this.takeIf {
            it.isNotBlank()
        }?.let {
            "$prefix$it"
        }?: ""
    }
}
