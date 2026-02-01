package com.krzysobo.soboapptpl.settings

import com.russhwolf.settings.Settings

interface AppSettingsWorker {
    fun getString(key: String, defValue: String): String
    fun putString(key: String, value: String)

    fun getBoolean(key: String, defValue: Boolean): Boolean
    fun putBoolean(key: String, value: Boolean)
}


// for RusshwolfSettings
class AppSettingsWorkerRW : AppSettingsWorker {
    private val settings: Settings = Settings()

    override fun getString(key: String, defValue: String): String {
        return settings.getString(key, defValue)
    }

    override fun putString(key: String, value: String) {
        settings.putString(key, value)
    }

    override fun getBoolean(key: String, defValue: Boolean): Boolean {
        return settings.getBoolean(key, defValue)
    }

    override fun putBoolean(key: String, value: Boolean) {
        settings.putBoolean(key, value)
    }

}

