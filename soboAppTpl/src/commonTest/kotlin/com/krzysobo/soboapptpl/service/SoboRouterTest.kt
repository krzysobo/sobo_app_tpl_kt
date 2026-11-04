package com.krzysobo.soboapptpl.service

import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertSame
import kotlin.test.assertTrue

/**
 * SoboRouter is a process-wide singleton, so every test starts from a freshly initialized router
 * with an empty back stack.
 */
class SoboRouterTest {
    private val welcome = SoboRoute(handle = "welcome")
    private val login = SoboRoute(handle = "login", perms = listOf(USER_ROLE.ANON_ONLY))
    private val userHome = SoboRoute(handle = "user_home", perms = listOf(USER_ROLE.USER))
    private val adminPanel = SoboRoute(handle = "admin", perms = listOf(USER_ROLE.ADMIN))
    private val about = SoboRoute(handle = "about")

    private var loggedIn = false
    private var admin = false

    class DetailsRequest(val id: Int) : AppRequest()

    @BeforeTest
    fun setUp() {
        loggedIn = false
        admin = false
        SoboRouter.backStack.clear()
        SoboRouter.maxBackStackSize = 20
        SoboRouter.initRouter(
            routes = listOf(welcome, login, userHome, adminPanel, about),
            routeHandleAnonymousUserHome = "welcome",
            routeHandleLoggedInUserHome = "user_home",
            routeHandleWelcome = "welcome",
            routeHandleLogin = "login",
            lmbIsLoggedIn = { loggedIn },
            lmbIsLoggedInAdmin = { admin },
            authUsed = true,
        )
        SoboRouter.navigateToEmpty()
    }

    @AfterTest
    fun tearDown() {
        SoboRouter.backStack.clear()
        SoboRouter.maxBackStackSize = 20
    }

    // ---- navigation & back stack ----

    @Test
    fun navigateToRouteHandle_setsCurrentRouteAndRequest() {
        val req = DetailsRequest(7)
        SoboRouter.navigateToRouteHandle("about", req)

        assertEquals("about", SoboRouter.getCurrentRoute().handle)
        assertEquals("about", SoboRouter.currentRouteHandle.value)
        assertSame(req, SoboRouter.getCurrentRequest())
        assertTrue(SoboRouter.isRouteSet())
    }

    @Test
    fun navigateToUnknownRoute_throws() {
        assertFailsWith<NoSuchElementException> {
            SoboRouter.navigateToRouteHandle("does_not_exist")
        }
    }

    @Test
    fun navigateToEmpty_doesNotTouchBackStack() {
        SoboRouter.navigateToRouteHandle("welcome")
        SoboRouter.navigateToEmpty()

        assertFalse(SoboRouter.isRouteSet())
        assertEquals(1, SoboRouter.backStack.size)
    }

    @Test
    fun goBack_returnsToPreviousRouteWithItsRequest() {
        val req = DetailsRequest(1)
        SoboRouter.navigateToRouteHandle("welcome", req)
        SoboRouter.navigateToRouteHandle("about")

        assertTrue(SoboRouter.canGoBack())
        assertTrue(SoboRouter.canGoBackRmb.value)
        assertTrue(SoboRouter.goBackIfPossible())

        assertEquals("welcome", SoboRouter.getCurrentRoute().handle)
        assertSame(req, SoboRouter.getCurrentRequest())
        assertEquals(1, SoboRouter.backStack.size)
        assertFalse(SoboRouter.canGoBackRmb.value)
    }

    @Test
    fun goBack_onSingleItem_isNotPossibleAndAppMayClose() {
        SoboRouter.navigateToRouteHandle("welcome")

        assertFalse(SoboRouter.goBackIfPossible())
        assertEquals("welcome", SoboRouter.getCurrentRoute().handle)
        assertTrue(SoboRouter.canCloseAppWithBackButton())
    }

    @Test
    fun reenteringTheTopRoute_isNotAddedTwice() {
        SoboRouter.navigateToRouteHandle("welcome")
        SoboRouter.navigateToRouteHandle("about")
        SoboRouter.navigateToRouteHandle("about")

        assertEquals(listOf("welcome", "about"), SoboRouter.backStack.map { it.route.handle })
    }

    @Test
    fun sameRouteWithDifferentRequest_isAddedToBackStack() {
        SoboRouter.navigateToRouteHandle("about", DetailsRequest(1))
        SoboRouter.navigateToRouteHandle("about", DetailsRequest(2))

        assertEquals(2, SoboRouter.backStack.size)
    }

    @Test
    fun backStack_actsAsRing_droppingOldestItems() {
        SoboRouter.maxBackStackSize = 3
        SoboRouter.navigateToRouteHandle("welcome")
        SoboRouter.navigateToRouteHandle("about")
        SoboRouter.navigateToRouteHandle("welcome")
        SoboRouter.navigateToRouteHandle("about")

        assertEquals(listOf("about", "welcome", "about"), SoboRouter.backStack.map { it.route.handle })
    }

    @Test
    fun resetBackStack_startsFreshHistory() {
        SoboRouter.navigateToRouteHandle("welcome")
        SoboRouter.navigateToRouteHandle("about")

        SoboRouter.resetBackStack("welcome")

        assertEquals(listOf("welcome"), SoboRouter.backStack.map { it.route.handle })
        assertFalse(SoboRouter.canGoBack())
    }

    @Test
    fun previousBackStackItem_isAvailableOnlyWithHistory() {
        SoboRouter.navigateToRouteHandle("welcome")
        assertNull(SoboRouter.getPreviousBackStackItemIfAvailable())

        SoboRouter.navigateToRouteHandle("about")
        assertEquals("welcome", SoboRouter.getPreviousBackStackItemIfAvailable()?.route?.handle)
    }

    @Test
    fun navigateToWelcomeIfBackStackEmpty_onlyWhenEmpty() {
        SoboRouter.navigateToWelcomeIfBackStackEmpty()
        assertEquals("welcome", SoboRouter.getCurrentRoute().handle)

        SoboRouter.navigateToRouteHandle("about")
        SoboRouter.navigateToWelcomeIfBackStackEmpty()
        assertEquals("about", SoboRouter.getCurrentRoute().handle)
    }

    // ---- permissions ----

    private fun applyPermsFor(handle: String): String {
        SoboRouter.navigateToRouteHandle(handle)
        SoboRouter.applyPermsToRoute(SoboRouter.getCurrentRoute())
        return SoboRouter.getCurrentRoute().handle
    }

    @Test
    fun anyUserRoute_isAlwaysAllowed() {
        assertEquals("about", applyPermsFor("about"))
        loggedIn = true
        assertEquals("about", applyPermsFor("about"))
    }

    @Test
    fun anonOnlyRoute_redirectsLoggedInUserToHome() {
        assertEquals("login", applyPermsFor("login"))
        loggedIn = true
        assertEquals("user_home", applyPermsFor("login"))
    }

    @Test
    fun userRoute_redirectsAnonymousToLogin() {
        assertEquals("login", applyPermsFor("user_home"))
        loggedIn = true
        assertEquals("user_home", applyPermsFor("user_home"))
    }

    @Test
    fun adminRoute_allowsOnlyAdmins() {
        assertEquals("login", applyPermsFor("admin"))       // anonymous -> login
        loggedIn = true
        assertEquals("user_home", applyPermsFor("admin"))   // plain user -> own home
        admin = true
        assertEquals("admin", applyPermsFor("admin"))       // admin -> allowed
    }

    @Test
    fun withoutAuth_everyRouteIsAllowed() {
        SoboRouter.authUsed = false
        assertEquals("admin", applyPermsFor("admin"))
        assertEquals("user_home", applyPermsFor("user_home"))
    }

    @Test
    fun navigateToDefaultRoute_dependsOnAuth() {
        SoboRouter.navigateToDefaultRoute()
        assertEquals("login", SoboRouter.getCurrentRoute().handle)

        SoboRouter.authUsed = false
        SoboRouter.navigateToDefaultRoute()
        assertEquals("welcome", SoboRouter.getCurrentRoute().handle)
    }

    @Test
    fun navigateToLoggedInUserHome_onlyForLoggedInUsers() {
        SoboRouter.navigateToRouteHandle("about")
        SoboRouter.navigateToLoggedInUserHome()
        assertEquals("about", SoboRouter.getCurrentRoute().handle)

        loggedIn = true
        SoboRouter.navigateToLoggedInUserHome()
        assertEquals("user_home", SoboRouter.getCurrentRoute().handle)
    }

    @Test
    fun navigateToLogin_skippedWhenAlreadyLoggedIn() {
        loggedIn = true
        SoboRouter.navigateToRouteHandle("about")
        SoboRouter.navigateToLogin()
        assertEquals("about", SoboRouter.getCurrentRoute().handle)
    }
}
