package com.park9eon.compose

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class URLPatternMatchTests {

    @Test
    fun testUrlPatternMatchWhenPatternEqualsPath() {
        val pattern = "/foo"
        val path = "/foo"

        val result: URLPatternResult? = urlPatternMatch(pattern = pattern, path = path)

        assertNotNull(result)
        assertEquals(path, result.path)
        assertEquals(path, result.pathname)
    }

    @Test
    fun testUrlPatternMatchWhenPatternEqualsPathWithTrailingSlash() {
        val pattern = "/foo"
        val path = "/foo/"

        val result: URLPatternResult? = urlPatternMatch(pattern = pattern, path = path)

        assertNotNull(result)
        assertEquals(path, result.path)
        assertEquals("/foo/", result.pathname)
    }

    @Test
    fun testUrlPatternMatchWhenPatternEqualsPathWithTrailingQuestion() {
        val pattern = "/foo"
        val path = "/foo?"

        val result: URLPatternResult? = urlPatternMatch(pattern = pattern, path = path)

        assertNotNull(result)
        assertEquals(path, result.path)
        assertEquals("/foo", result.pathname)
        assertEquals("", result.search)
    }

    @Test
    fun testUrlPatternMatchWhenPatternEqualsPathWithTrailingHash() {
        val pattern = "/foo"
        val path = "/foo#"

        val result: URLPatternResult? = urlPatternMatch(pattern = pattern, path = path)

        assertNotNull(result)
        assertEquals(path, result.path)
        assertEquals("/foo", result.pathname)
        assertEquals("", result.hash)
    }

    @Test
    fun testUrlPatternMatchWhenPatternNotEqualsPath() {
        val pattern = "/foo"
        val path = "/bar"

        val result: URLPatternResult? = urlPatternMatch(pattern = pattern, path = path)

        assertNull(result)
    }

    @Test
    fun testUrlPatternMatchWhenPathHasQueryParams() {
        val pattern = "/foo"
        val path = "/foo?foo=bar"

        val result: URLPatternResult? = urlPatternMatch(pattern = pattern, path = path)

        assertNotNull(result)
        assertEquals(path, result.path)
        assertEquals("?foo=bar", result.search)
        assertEquals("/foo", result.pathname)
        assertEquals(1, result.params.size)
        assertEquals("bar", result.params["foo"])
        assertEquals("bar", result["foo"])
    }

    @Test
    fun testUrlPatternMatchWhenPatternHasSlug() {
        val pattern = "/foo/:slug"
        val path = "/foo/bar"

        val result: URLPatternResult? = urlPatternMatch(pattern = pattern, path = path)

        assertNotNull(result)
        assertEquals(path, result.path)
        assertEquals("/foo/bar", result.pathname)
        assertEquals(1, result.params.size)
        assertEquals("bar", result.params["slug"])
        assertEquals("bar", result["slug"])
    }

    @Test
    fun testUrlPatternMatchWhenHasHash() {
        val pattern = "/foo"
        val path = "/foo#abc"

        val result: URLPatternResult? = urlPatternMatch(pattern = pattern, path = path)

        assertNotNull(result)
        assertEquals(path, result.path)
        assertEquals("/foo", result.pathname)
        assertEquals("#abc", result.hash)
    }

    @Test
    fun testUrlPatternMatchFull() {
        val pattern = "/foo/:slug"
        val path = "/foo/bar?q=baz#abc"

        val result: URLPatternResult? = urlPatternMatch(pattern = pattern, path = path)

        assertNotNull(result)
        assertEquals(path, result.path)
        assertEquals("/foo/bar", result.pathname)
        assertEquals("baz", result["q"])
        assertEquals("#abc", result.hash)
    }

    @Test
    fun testUrlPatternMatchWhenEscaped() {
        val pattern = "/[:time]-[:id]/^[]$.{}()|+*:;'`,"
        val path = "/[1]-[2]/^[]$.{}()|+*:;'`,"

        val result: URLPatternResult? = urlPatternMatch(pattern = pattern, path = path)

        assertNotNull(result)
        assertEquals(path, result.path)
        assertEquals("/[1]-[2]/^[]$.{}()|+*:;'`,", result.pathname)
        assertEquals("1", result["time"])
        assertEquals("2", result["id"])
    }

}

