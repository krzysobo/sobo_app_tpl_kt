package com.krzysobo.soboapptpl.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.krzysobo.soboapptpl.service.SoboRouter


object AppViewModelVM : SoboViewModel() {
    var isDarkMode: MutableState<Boolean> = mutableStateOf(true)
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