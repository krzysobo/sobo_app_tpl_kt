package com.krzysobo.soboapptpl.service

import kotlin.test.Test
import kotlin.test.assertEquals

class RouteFilteringTest {
    private val routes = listOf(
        SoboRoute(handle = "public", perms = listOf(USER_ROLE.ANY_USER)),
        SoboRoute(handle = "login", perms = listOf(USER_ROLE.ANON_ONLY)),
        SoboRoute(handle = "profile", perms = listOf(USER_ROLE.USER)),
        SoboRoute(handle = "admin", perms = listOf(USER_ROLE.ADMIN)),
        SoboRoute(handle = "hidden", perms = listOf(USER_ROLE.ANY_USER)),
    )
    private val menu = listOf("public", "login", "profile", "admin")

    private fun handles(list: List<SoboRoute>) = list.map { it.handle }

    @Test
    fun withoutUser_keepsMenuRoutesInRouteOrder() {
        val res = filterRoutesForMenuWithoutUser(listOf("admin", "public"), routes)
        assertEquals(listOf("public", "admin"), handles(res))
    }

    @Test
    fun anonymous_seesAnyUserAndAnonOnlyRoutes() {
        val res = filterRoutesByUserStatus(menu, routes, isLoggedIn = false, isAdmin = false)
        assertEquals(listOf("public", "login"), handles(res))
    }

    @Test
    fun loggedInUser_seesAnyUserAndUserRoutes() {
        val res = filterRoutesByUserStatus(menu, routes, isLoggedIn = true, isAdmin = false)
        assertEquals(listOf("public", "profile"), handles(res))
    }

    @Test
    fun admin_seesEverythingExceptAnonOnly() {
        val res = filterRoutesByUserStatus(menu, routes, isLoggedIn = true, isAdmin = true)
        assertEquals(listOf("public", "profile", "admin"), handles(res))
    }

    @Test
    fun routesOutsideTheMenu_areNeverReturned() {
        val res = filterRoutesByUserStatus(listOf(), routes, isLoggedIn = true, isAdmin = true)
        assertEquals(emptyList(), handles(res))
    }
}
