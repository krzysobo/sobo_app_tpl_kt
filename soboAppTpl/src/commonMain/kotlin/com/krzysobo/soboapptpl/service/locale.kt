package com.krzysobo.soboapptpl.service

import com.krzysobo.soboapptpl.settings.AppSettingsLang
import java.util.Locale


interface LocaleWorker {
    fun storeOriginalSystemLocale()
    fun useOriginalSystemLocale()
    fun setCurrentAppLocaleByLang(lang: String)
    fun getCurrentLang(): String
}

class LocaleWorkerJavaUtilLocale : LocaleWorker {
    private var originalSystemLocale: Locale? = null

    override fun storeOriginalSystemLocale() {
        println("TESTXX -- storeOriginalSystemLocale()")
        if (originalSystemLocale == null) {   // this may be done only ONCE in app running
            println("TESTXX -- storeOriginalSystemLocale() - it was null, so setting it up...")
            originalSystemLocale = Locale.getDefault()
            println("TESTXX -- storeOriginalSystemLocale() - it was null now it is: $originalSystemLocale...")
        }
    }

    override fun useOriginalSystemLocale() {
        println("TESTXX -- useOriginalSystemLocale() - $originalSystemLocale")
        if ((originalSystemLocale != null) &&
            (originalSystemLocale !== getCurrentAppLocale())
        ) {
            println("TESTXX -- useOriginalSystemLocale() NOW - $originalSystemLocale")
            Locale.setDefault(originalSystemLocale)
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

class LocaleWorkerAppSettingsOnly : LocaleWorker {
    private var originalSystemLocale: Locale? = null
    private var currentLanguage: String = ""

    override fun storeOriginalSystemLocale() {
        // unnecessary in this implementation (worker),
        // since this worker doesn't change the Locale value
//        println("TESTXX -- storeOriginalSystemLocale()")
//        if (originalSystemLocale == null) {   // this may be done only ONCE in app running
//            println("TESTXX -- storeOriginalSystemLocale() - it was null, so setting it up...")
//            originalSystemLocale = Locale.getDefault()
//            println("TESTXX -- storeOriginalSystemLocale() - it was null now it is: $originalSystemLocale...")
//        }
    }

    override fun useOriginalSystemLocale() {
        println("TESTXX -- useOriginalSystemLocale()")
        currentLanguage = Locale.getDefault().language
    }

    override fun setCurrentAppLocaleByLang(lang: String) {
        currentLanguage = lang
        println("TESTXX --- setCurrentAppLocaleByLangQQQ - $lang")
//        Locale.setDefault(Locale.forLanguageTag(lang))
    }

    override fun getCurrentLang(): String {
        return currentLanguage
    }

//    private fun getCurrentAppLocale(): Locale {
//        return Locale.getDefault()
//    }
}


object LocaleManager {
    //    var localeWorker: LocaleWorker = LocaleWorkerJavaUtilLocale()
    var localeWorker: LocaleWorker = LocaleWorkerAppSettingsOnly()


    fun getCurrentLang(): String {
        return localeWorker.getCurrentLang()
    }

    fun storeOriginalLocale() {
        localeWorker.storeOriginalSystemLocale()
    }

    fun useLocaleFromAppSettings() {
//        println("TESTXX --- LocaleManager -- useLocaleFromAppSettings SYSLANG: ${AppSettingsLang.useSystemLang} LANG: ${AppSettingsLang.lang}")
        if (AppSettingsLang.useSystemLang) {
//            println("TESTXX --- LocaleManager -- useLocaleFromAppSettings - using original locale ")
//            println("TESTXX --- LocaleManager -- useLocaleFromAppSettings - EditableSettings LANG: ${AppSettingsLang.lang} ")
            useOriginalLocale()
        } else if ((AppSettingsLang.lang != "")) {
//            println("TESTXX --- LocaleManager -- useLocaleFromAppSettings - setting NEW LANG TO ${AppSettingsLang.lang} ")
            setAppLocaleByLang(AppSettingsLang.lang)
        }
//        countLocaleUpdated()
    }


    fun setAppLocaleByLang(lang: String) {
        localeWorker.setCurrentAppLocaleByLang(lang)
    }

    private fun useOriginalLocale() {
        localeWorker.useOriginalSystemLocale()
//        countLocaleUpdated()
    }

}

//    private fun countLocaleUpdated() {
//        if (localeUpdated.value < 20000) {
//            println("TESTXX -- countLocaleUpdated() -- LOCALE UPDATED: ${localeUpdated.value}")
//            localeUpdated.value += 1
//        } else {
//            localeUpdated.value = 0
//        }
//    }
//    var localeUpdated: MutableState<Int> = mutableStateOf(0);