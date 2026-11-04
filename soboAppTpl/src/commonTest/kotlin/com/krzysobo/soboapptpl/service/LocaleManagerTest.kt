package com.krzysobo.soboapptpl.service

import java.util.Locale
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class LocaleManagerTest {
    private lateinit var savedWorker: LocaleWorker
    private lateinit var savedDefaultLocale: Locale

    @BeforeTest
    fun setUp() {
        savedWorker = LocaleManager.localeWorker
        savedDefaultLocale = Locale.getDefault()
    }

    @AfterTest
    fun tearDown() {
        LocaleManager.localeWorker = savedWorker
        Locale.setDefault(savedDefaultLocale)
    }

    @Test
    fun appSettingsOnlyWorker_keepsLanguageWithoutTouchingJvmLocale() {
        Locale.setDefault(Locale.GERMANY)
        LocaleManager.localeWorker = LocaleWorkerAppSettingsOnly()

        LocaleManager.setAppLocaleByLang("pl")

        assertEquals("pl", LocaleManager.getCurrentLang())
        assertEquals(Locale.GERMANY, Locale.getDefault())
    }

    @Test
    fun appSettingsOnlyWorker_originalLocale_isTheJvmDefault() {
        Locale.setDefault(Locale.FRANCE)
        val worker = LocaleWorkerAppSettingsOnly()
        worker.setCurrentAppLocaleByLang("pl")

        worker.useOriginalSystemLocale()

        assertEquals("fr", worker.getCurrentLang())
    }

    @Test
    fun javaUtilLocaleWorker_changesAndRestoresTheJvmDefault() {
        Locale.setDefault(Locale.UK)
        val worker = LocaleWorkerJavaUtilLocale()
        worker.storeOriginalSystemLocale()

        worker.setCurrentAppLocaleByLang("ru")
        assertEquals("ru", worker.getCurrentLang())
        assertEquals("ru", Locale.getDefault().language)

        worker.useOriginalSystemLocale()
        assertEquals(Locale.UK, Locale.getDefault())
    }

    @Test
    fun javaUtilLocaleWorker_storesTheOriginalLocaleOnlyOnce() {
        Locale.setDefault(Locale.UK)
        val worker = LocaleWorkerJavaUtilLocale()
        worker.storeOriginalSystemLocale()
        worker.setCurrentAppLocaleByLang("es")
        worker.storeOriginalSystemLocale()    // must not overwrite "en-GB" with "es"

        worker.useOriginalSystemLocale()

        assertEquals(Locale.UK, Locale.getDefault())
    }
}
