package com.krzysobo.soboapptpl.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.krzysobo.soboapptpl.settings.AppSettingsLang
import com.krzysobo.soboapptpl.widgets.strNotEmpty
import com.krzysobo.soboapptpl.widgets.validateWithLambda

class LangSettingsVM : SoboViewModel() {
    var useSystemLang: MutableState<Boolean> = mutableStateOf(false);
    var lang: MutableState<String> = mutableStateOf("")
    var isErrorLang: MutableState<Boolean> = mutableStateOf(false)

    fun doRefreshSettingsFromAppSettings() {
        lang.value = AppSettingsLang.lang
        useSystemLang.value = AppSettingsLang.useSystemLang
    }

    fun doUpdateAppSettings(): Boolean {
//        println("\n\n======================= UPDATE SETTINGS ===============\n\n")
        try {
            AppSettingsLang.lang = lang.value
            AppSettingsLang.useSystemLang = useSystemLang.value
            isFormSent.value = true

            return true
        } catch (e: Exception) {
            isApiError.value = true
            apiErrorDetails.value = "${e.message}"
        }

        return false
    }

    fun validate(): Boolean {
        clearErrors()

        val resLang = validateWithLambda(isErrorLang, { strNotEmpty(lang.value) })
        return resLang
    }

    fun clearLangError() {
        clearErrorFlag(isErrorLang)
    }

    fun clearErrors() {
        clearLangError()

        clearApiError()
        clearFormSent()
    }

}
