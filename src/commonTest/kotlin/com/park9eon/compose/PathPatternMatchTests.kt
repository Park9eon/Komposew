package com.park9eon.compose

import com.park9eon.compose.PathPatternUtils.get
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class PathPatternMatchTests {

    @Test
    fun testPathPatternMatchWhenPatternEqualsPath() {
        val pattern = "/foo"
        val path = "/foo"

        val result: PathPatternResult? = pathPatternMatch(pattern = pattern, path = path)

        assertNotNull(result)
        assertEquals(path, result.path)
        assertEquals(path, result.pathname)
    }

    @Test
    fun testPathPatternMatchWhenPatternEqualsPathWithTrailingSlash() {
        val pattern = "/foo"
        val path = "/foo/"

        val result: PathPatternResult? = pathPatternMatch(pattern = pattern, path = path)

        assertNotNull(result)
        assertEquals(path, result.path)
        assertEquals("/foo/", result.pathname)
    }

    @Test
    fun testPathPatternMatchWhenPatternEqualsPathWithTrailingQuestion() {
        val pattern = "/foo"
        val path = "/foo?"

        val result: PathPatternResult? = pathPatternMatch(pattern = pattern, path = path)

        assertNotNull(result)
        assertEquals(path, result.path)
        assertEquals("/foo", result.pathname)
        assertEquals("", result.search)
    }

    @Test
    fun testPathPatternMatchWhenPatternEqualsPathWithTrailingHash() {
        val pattern = "/foo"
        val path = "/foo#"

        val result: PathPatternResult? = pathPatternMatch(pattern = pattern, path = path)

        assertNotNull(result)
        assertEquals(path, result.path)
        assertEquals("/foo", result.pathname)
        assertEquals("", result.hash)
    }

    @Test
    fun testPathPatternMatchWhenPatternNotEqualsPath() {
        val pattern = "/foo"
        val path = "/bar"

        val result: PathPatternResult? = pathPatternMatch(pattern = pattern, path = path)

        assertNull(result)
    }

    @Test
    fun testPathPatternMatchWhenPathHasQueryParams() {
        val pattern = "/foo"
        val path = "/foo?foo=bar"

        val result: PathPatternResult? = pathPatternMatch(pattern = pattern, path = path)

        assertNotNull(result)
        assertEquals(path, result.path)
        assertEquals("?foo=bar", result.search)
        assertEquals("/foo", result.pathname)
        assertEquals(1, result.params.size)
        assertEquals("bar", result.params["foo"])
        assertEquals("bar", result["foo"])
    }

    @Test
    fun testPathPatternMatchWhenPatternHasSlug() {
        val pattern = "/foo/:slug"
        val path = "/foo/bar"

        val result: PathPatternResult? = pathPatternMatch(pattern = pattern, path = path)

        assertNotNull(result)
        assertEquals(path, result.path)
        assertEquals("/foo/bar", result.pathname)
        assertEquals(1, result.params.size)
        assertEquals("bar", result.params["slug"])
        assertEquals("bar", result["slug"])
    }

    @Test
    fun testPathPatternMatchWhenHasHash() {
        val pattern = "/foo"
        val path = "/foo#abc"

        val result: PathPatternResult? = pathPatternMatch(pattern = pattern, path = path)

        assertNotNull(result)
        assertEquals(path, result.path)
        assertEquals("/foo", result.pathname)
        assertEquals("#abc", result.hash)
    }

    @Test
    fun testPathPatternMatchFull() {
        val pattern = "/foo/:slug"
        val path = "/foo/bar?q=baz#abc"

        val result: PathPatternResult? = pathPatternMatch(pattern = pattern, path = path)

        assertNotNull(result)
        assertEquals(path, result.path)
        assertEquals("/foo/bar", result.pathname)
        assertEquals("baz", result["q"])
        assertEquals("#abc", result.hash)
    }

    @Test
    fun testPathPatternMatchWhenEscaped() {
        val pattern = "/[:time]-[:id]/^[]$.{}()|+*:;'`,"
        val path = "/[1]-[2]/^[]$.{}()|+*:;'`,"

        val result: PathPatternResult? = pathPatternMatch(pattern = pattern, path = path)

        assertNotNull(result)
        assertEquals(path, result.path)
        assertEquals("/[1]-[2]/^[]$.{}()|+*:;'`,", result.pathname)
        assertEquals("1", result["time"])
        assertEquals("2", result["id"])
    }

}

