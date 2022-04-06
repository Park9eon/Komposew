package com.park9eon.compose

import kotlin.test.Test
import kotlin.test.assertEquals

class URLPatternToSearchTests {

    @Test
    fun testMapToSearch() {
        var search = mapOf(
            "q" to "foo",
        ).toSearch()

        assertEquals("?q=foo", search)

        search = mapOf(
            "foo" to "1",
            "bar" to "2",
        ).toSearch()

        assertEquals("?foo=1&bar=2", search)
    }

    @Test
    fun testMapToSearchWhenMapIsEmpty() {
        val search = emptyMap<String, String>(
        ).toSearch()

        assertEquals("", search)
    }

    @Test
    fun testMapToSearchWhenHasNull() {
        var search = mapOf<String, String?>(
            "q" to null,
        ).toSearch()

        assertEquals("?q=", search)

        search = mapOf(
            "foo" to null,
            "bar" to "null",
        ).toSearch()

        assertEquals("?foo=&bar=null", search)
    }

    @Test
    fun testMapToSearchWhenHasAnyType() {
        var search = mapOf<String, Any?>(
            "string" to "bar",
            "number" to 1,
        ).toSearch()

        assertEquals("?string=bar&number=1", search)

        search = mapOf<String, Any?>(
            "string" to "bar",
            "number" to 1,
            "double" to 1.2,
            "long" to 100L,
        ).toSearch()

        assertEquals("?string=bar&number=1&double=1.2&long=100", search)
    }

}
