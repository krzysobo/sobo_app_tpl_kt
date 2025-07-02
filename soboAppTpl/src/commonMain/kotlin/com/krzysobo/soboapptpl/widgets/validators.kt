package com.krzysobo.soboapptpl.widgets

import androidx.compose.runtime.MutableState
import java.util.regex.Matcher
import java.util.regex.Pattern


fun strNotEmpty(value: String): Boolean {

    return value != ""
}

fun strIsHex(value: String): Boolean {
    val x = 0
    return true
}

val PATTERN_HEX_ANY_LENGTH = Pattern.compile("\\p{XDigit}+")
val PATTERN_HEX_SPECIFIED_LENGTH = { it: Int -> Pattern.compile("\\p{XDigit}{${it}}") }

fun isHex(input: String, patt: Pattern = PATTERN_HEX_ANY_LENGTH): Boolean {
    val matcher: Matcher = patt.matcher(input)
    return matcher.matches()
}

fun isHexSpecifiedLength(
    input: String,
    length: Int,
    patt: Pattern = PATTERN_HEX_SPECIFIED_LENGTH(length)
): Boolean {
    val matcher: Matcher = patt.matcher(input)
    return matcher.matches()
}


fun validateWithLambda(errField: MutableState<Boolean>, valFunc: () -> Boolean): Boolean {
    val res = valFunc()
//    print("validateWithLambda res: $res")
    errField.value = !res

    return res
}



