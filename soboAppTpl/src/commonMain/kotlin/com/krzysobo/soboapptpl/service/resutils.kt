package com.krzysobo.soboapptpl.service

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.intl.Locale
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource

fun formatIt(text: String, args: Array<Any>): String {
    val textOut = text.format(args)
    return textOut
}

fun clue() {
    println(formatIt("Hello %1\$s! How are you? ", arrayOf("Jack")))
}

class SoboStringResource(
    val langVer: HashMap<String, String> = hashMapOf()
) {

    fun toStringForLang(lang: String = "en"): String? {
        if (langVer.containsKey(lang)) {
            return langVer[lang]
        }

        return ""
    }
}

class AnyRes() {
    private var resArgs: Array<Any> = emptyArray()
    private var text: String = ""
    private var resKmp: StringResource? = null
    private var soboRes: SoboStringResource? = null


    constructor(res: StringResource?, resArgs: Array<Any> = emptyArray()) : this() {
        resKmp = res
        this.resArgs = resArgs
    }

    constructor(res: StringResource?, argX: String) : this() {
        resKmp = res
        this.resArgs = arrayOf(argX)
    }

    constructor(res: StringResource?, argX: Int) : this() {
        resKmp = res
        this.resArgs = arrayOf(argX)
    }

    constructor(res: StringResource?, argX: Float) : this() {
        resKmp = res
        this.resArgs = arrayOf(argX)
    }

    constructor(res: StringResource?, argX: Double) : this() {
        resKmp = res
        this.resArgs = arrayOf(argX)
    }

    constructor(res: StringResource?, argX: Char) : this() {
        resKmp = res
        this.resArgs = arrayOf(argX)
    }

    constructor(soboRes: SoboStringResource, resArgs: Array<Any> = emptyArray()) : this() {
        this.soboRes = soboRes
        this.resArgs = resArgs
    }

    constructor(textIn: String) : this() {
        text = textIn
    }

    suspend fun toStringNonCompose(): String {
        if (soboRes != null) {
            val lang = LocaleManager.getCurrentLang()
            val resText = soboRes?.toStringForLang(lang)
            val resTextOut = if (resArgs.size > 0) {
                resText?.format(*resArgs)
            } else {
                resText
            }

            return resTextOut ?: ""
        }  else if (resKmp != null) {
            val resOut = if (resArgs.size > 0) {
                getString(resKmp!!, *resArgs)
            } else {
                getString(resKmp!!)
            }

            return resOut

        }

        return ""
    }

    @Composable
    fun getText(): String {
        if (soboRes != null) {
            val lang = LocaleManager.getCurrentLang()
            val resText = soboRes?.toStringForLang(lang)
            val resTextOut = if (resArgs.size > 0) {
                resText?.format(*resArgs)
            } else {
                resText
            }

            return resTextOut ?: ""

        } else if (resKmp != null) {
            val resOut = if (resArgs.size > 0) {
                stringResource(resKmp!!, *resArgs)
            } else {
                stringResource(resKmp!!)
            }

            return resOut

        }

        return text
    }
}


@Composable
fun anyResText(anyRes: AnyRes?): String {
    return anyRes?.getText() ?: ""

}
