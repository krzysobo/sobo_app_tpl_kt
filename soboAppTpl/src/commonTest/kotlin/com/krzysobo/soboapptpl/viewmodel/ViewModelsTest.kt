package com.krzysobo.soboapptpl.viewmodel

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ViewModelsTest {
    @Test
    fun appViewModel_layoutAndMenuToggles() {
        AppViewModelVM.showLayout()
        AppViewModelVM.showMenu()
        assertTrue(AppViewModelVM.isLayoutShown.value)
        assertTrue(AppViewModelVM.isMenuShown.value)

        AppViewModelVM.toggleShowLayout()
        AppViewModelVM.hideMenu()
        assertFalse(AppViewModelVM.isLayoutShown.value)
        assertFalse(AppViewModelVM.isMenuShown.value)

        AppViewModelVM.hideLayout()
        AppViewModelVM.toggleShowMenu()
        assertFalse(AppViewModelVM.isLayoutShown.value)
        assertTrue(AppViewModelVM.isMenuShown.value)
    }

    @Test
    fun darkModeAndRefreshToggles_flipTheirState() {
        val dark = isDarkMode()
        toggleDarkMode()
        assertEquals(!dark, isDarkMode())
        toggleDarkMode()
        assertEquals(dark, isDarkMode())

        val refresh = isRefreshCompose()
        toggleRefreshCompose()
        assertEquals(!refresh, isRefreshCompose())
    }

    @Test
    fun langSettingsVM_validate_requiresLanguage() {
        val vm = LangSettingsVM()
        vm.isFormSent.value = true
        vm.isApiError.value = true

        vm.lang.value = ""
        assertFalse(vm.validate())
        assertTrue(vm.isErrorLang.value)
        // validate() starts from a clean state
        assertFalse(vm.isFormSent.value)
        assertFalse(vm.isApiError.value)

        vm.lang.value = "pl"
        assertTrue(vm.validate())
        assertFalse(vm.isErrorLang.value)
    }

    @Test
    fun langSettingsVM_clearLangError() {
        val vm = LangSettingsVM()
        vm.isErrorLang.value = true
        vm.clearLangError()
        assertFalse(vm.isErrorLang.value)
    }
}
