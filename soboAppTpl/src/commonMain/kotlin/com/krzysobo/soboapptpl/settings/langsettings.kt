package com.krzysobo.soboapptpl.settings
import com.russhwolf.settings.Settings

object AppSettingsLang {
    private val settings: Settings = Settings()

    var useSystemLang: Boolean
        get() = settings.getBoolean("use_system_lang", true)
        set(value) = settings.putBoolean("use_system_lang", value)

    var lang: String
        get() = settings.getString("lang", "en")
        set(value) = settings.putString("lang", value)
}
