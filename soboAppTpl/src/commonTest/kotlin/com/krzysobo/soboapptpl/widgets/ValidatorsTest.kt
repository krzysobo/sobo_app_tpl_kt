package com.krzysobo.soboapptpl.widgets

import androidx.compose.runtime.mutableStateOf
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ValidatorsTest {
    @Test
    fun strNotEmpty_rejectsOnlyTheEmptyString() {
        assertTrue(strNotEmpty("a"))
        assertTrue(strNotEmpty(" "))
        assertFalse(strNotEmpty(""))
    }

    @Test
    fun isHex_acceptsHexDigitsOfAnyCaseAndLength() {
        assertTrue(isHex("0"))
        assertTrue(isHex("deadBEEF0123456789"))
        assertFalse(isHex(""))
        assertFalse(isHex("0x1F"))
        assertFalse(isHex("12 34"))
        assertFalse(isHex("g"))
    }

    @Test
    fun isHexSpecifiedLength_requiresExactLength() {
        assertTrue(isHexSpecifiedLength("a1b2", 4))
        assertFalse(isHexSpecifiedLength("a1b", 4))
        assertFalse(isHexSpecifiedLength("a1b2c", 4))
        assertFalse(isHexSpecifiedLength("a1bz", 4))
    }

    @Test
    fun validateWithLambda_setsErrorFlagToTheOppositeOfTheResult() {
        val err = mutableStateOf(false)

        assertFalse(validateWithLambda(err) { false })
        assertTrue(err.value)

        assertTrue(validateWithLambda(err) { true })
        assertFalse(err.value)
    }
}
