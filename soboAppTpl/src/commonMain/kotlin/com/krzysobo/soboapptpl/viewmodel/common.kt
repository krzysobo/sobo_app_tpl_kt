package com.krzysobo.soboapptpl.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.krzysobo.soboapptpl.service.SoboRouter


open class SoboViewModel : ViewModel() {
    var isApiError: MutableState<Boolean> = mutableStateOf(false)
    var isFormSent: MutableState<Boolean> = mutableStateOf(false)
    var apiErrorDetails: MutableState<String> = mutableStateOf("")

    protected fun clearErrorFlag(errorFlag: MutableState<Boolean>) {
        errorFlag.value = false
    }

    protected fun clearApiError() {
        clearErrorFlag(isApiError)
    }

    protected fun clearFormSent() {
        isFormSent.value = false
    }
}


object AppViewModelVM : SoboViewModel() {
    var isDarkMode: MutableState<Boolean> = mutableStateOf(false)
    var refreshCompose: MutableState<Boolean> = mutableStateOf(false)
    var isLayoutShown: MutableState<Boolean> = mutableStateOf(true)
    var isMenuShown: MutableState<Boolean> = mutableStateOf(true)

    fun hideLayout() {
        isLayoutShown.value = false
    }

    fun hideMenu() {
        isMenuShown.value = false
    }

    fun showLayout() {
        isLayoutShown.value = true
    }

    fun showMenu() {
        isMenuShown.value = true
    }

    fun toggleShowLayout() {
        isLayoutShown.value = !isLayoutShown.value
    }

    fun toggleShowMenu() {
        isMenuShown.value = !isMenuShown.value
    }
}

fun toggleDarkMode() {
    AppViewModelVM.isDarkMode.value = !AppViewModelVM.isDarkMode.value
    println(">> LIB APPTPL COMMON: toggleDarkMode: ${AppViewModelVM.isDarkMode.value}")
}

fun isDarkMode(): Boolean {
    val state = AppViewModelVM.isDarkMode.value
    println(">> LIB APPTPL COMMON: isDarkMode: $state")
    return state
}

fun toggleRefreshCompose() {
    AppViewModelVM.refreshCompose.value = !AppViewModelVM.refreshCompose.value
}

fun isRefreshCompose(): Boolean {
    return AppViewModelVM.refreshCompose.value
}

fun isCurrentRouteSet(): Boolean {
    return SoboRouter.isRouteSet()
}