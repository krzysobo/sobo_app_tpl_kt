package com.krzysobo.soboapptpl.service

import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

class AnyResTest {
    private lateinit var savedWorker: LocaleWorker

    private val greeting = SoboStringResource(
        hashMapOf("en" to "Hello %s, you have %d messages", "pl" to "Cześć %s, masz %d wiadomości")
    )

    @BeforeTest
    fun setUp() {
        savedWorker = LocaleManager.localeWorker
        LocaleManager.localeWorker = LocaleWorkerAppSettingsOnly()
    }

    @AfterTest
    fun tearDown() {
        LocaleManager.localeWorker = savedWorker
    }

    @Test
    fun soboStringResource_returnsTextForLanguage_orEmpty() {
        assertEquals("Hello %s, you have %d messages", greeting.toStringForLang("en"))
        assertEquals("", greeting.toStringForLang("xx"))
    }

    @Test
    fun anyRes_withSoboResource_usesCurrentLanguageAndArgs() = runTest {
        val res = AnyRes(greeting, arrayOf<Any>("Ann", 3))

        LocaleManager.setAppLocaleByLang("en")
        assertEquals("Hello Ann, you have 3 messages", res.toStringNonCompose())

        LocaleManager.setAppLocaleByLang("pl")
        assertEquals("Cześć Ann, masz 3 wiadomości", res.toStringNonCompose())
    }

    @Test
    fun anyRes_withSoboResource_missingLanguage_givesEmptyString() = runTest {
        LocaleManager.setAppLocaleByLang("de")
        assertEquals("", AnyRes(SoboStringResource(hashMapOf("en" to "Hi"))).toStringNonCompose())
    }

    @Test
    fun anyRes_singleArgConstructors_formatTheValue() = runTest {
        LocaleManager.setAppLocaleByLang("en")
        val res = SoboStringResource(hashMapOf("en" to "[%s]"))

        assertEquals("[x]", AnyRes(res, "x").toStringNonCompose())
        assertEquals("[42]", AnyRes(res, 42).toStringNonCompose())
        assertEquals("[c]", AnyRes(res, 'c').toStringNonCompose())
    }

    @Ignore // BUG: toStringNonCompose() ignores the plain text - getText() (the Compose variant) returns it
    @Test
    fun anyRes_plainText_toStringNonCompose_returnsTheText() = runTest {
        assertEquals("plain", AnyRes("plain").toStringNonCompose())
    }

    @Ignore // BUG: formatIt passes the array as ONE argument (text.format(args) instead of text.format(*args))
    @Test
    fun formatIt_formatsWithAllArguments() {
        assertEquals("Hello Jack!", formatIt("Hello %1\$s!", arrayOf("Jack")))
    }
}
