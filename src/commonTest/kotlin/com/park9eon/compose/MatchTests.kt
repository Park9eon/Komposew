package com.park9eon.compose

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class MatchTests {

    @Test
    fun testMatchWhenPatternEqualsPath() {
        val pattern = "/foo"
        val path = "/foo"

        val matchResult: MatchResult? = match(pattern = pattern, path = path)

        assertNotNull(matchResult)
        assertEquals(path, matchResult.path)
    }

    @Test
    fun testMatchWhenPatternNotEqualsPath() {
        val pattern = "/foo"
        val path = "/bar"

        val matchResult: MatchResult? = match(pattern = pattern, path = path)

        assertNull(matchResult)
    }

}
