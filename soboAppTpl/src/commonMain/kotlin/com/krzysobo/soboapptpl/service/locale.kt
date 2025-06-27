package com.krzysobo.soboapptpl.service

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.krzysobo.soboapptpl.settings.AppSettingsLang
import java.util.Locale


interface LocaleWorker {
    fun storeOriginalSystemLocale()
    fun useOriginalSystemLocale()
    fun setCurrentAppLocaleByLang(lang: String)
    fun getCurrentLang(): String
}

class LocaleWorkerJavaUtilLocale : LocaleWorker {
    private var originalLocale: Locale? = null

    override fun storeOriginalSystemLocale() {
        println("TESTXX -- storeOriginalSystemLocale()")
        if (originalLocale == null) {   // this may be done only ONCE in app running
            println("TESTXX -- storeOriginalSystemLocale() - it was null, so setting it up...")
            originalLocale = Locale.getDefault()
            println("TESTXX -- storeOriginalSystemLocale() - it was null now it is: $originalLocale...")
        }
    }

    override fun useOriginalSystemLocale() {
        println("TESTXX -- useOriginalSystemLocale()")
        if ((originalLocale != null) &&
            (originalLocale !== getCurrentAppLocale())
        ) {
            Locale.setDefault(originalLocale)
        }
    }

    override fun setCurrentAppLocaleByLang(lang: String) {
        Locale.setDefault(Locale.forLanguageTag(lang))
    }

    override fun getCurrentLang(): String {
        return getCurrentAppLocale().language
    }

    private fun getCurrentAppLocale(): Locale {
        return Locale.getDefault()
    }

}

object LocaleManager {
    var localeWorker: LocaleWorker = LocaleWorkerJavaUtilLocale()
    var localeUpdated: MutableState<Int> = mutableStateOf(0);

    private fun countLocaleUpdated() {
        if (localeUpdated.value < 20000) {
            println("TESTXX -- countLocaleUpdated() -- LOCALE UPDATED: ${localeUpdated.value}")
            localeUpdated.value += 1
        } else {
            localeUpdated.value = 0
        }
    }

    fun storeOriginalLocale() {
        localeWorker.storeOriginalSystemLocale()
    }

    fun useOriginalLocale() {
        localeWorker.useOriginalSystemLocale()
//        countLocaleUpdated()
    }

    fun useLocaleFromAppSettings() {
        println("TESTXX --- LocaleManager -- useLocaleFromAppSettings SYSLANG: ${AppSettingsLang.useSystemLang} LANG: ${AppSettingsLang.lang}")
        if (AppSettingsLang.useSystemLang) {
            println("TESTXX --- LocaleManager -- useLocaleFromAppSettings - using original locale ")
            useOriginalLocale()
        } else if ((AppSettingsLang.lang != "")) {
            println("TESTXX --- LocaleManager -- useLocaleFromAppSettings - setting NEW LANG TO ${AppSettingsLang.lang} ")
            setAppLocaleByLang(AppSettingsLang.lang)
        }
//        countLocaleUpdated()
    }

    private fun setAppLocaleByLang(lang: String) {
        localeWorker.setCurrentAppLocaleByLang(lang)
    }

}
