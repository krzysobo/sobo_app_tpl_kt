package com.krzysobo.soboapptpl.service

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

open class AppRequest

enum class USER_ROLE(val roleId: Int) {
    ANY_USER(1),
    ANON_ONLY(2),
    USER(10),
    ADMIN(20)
}

data class SoboRoute(
    val handle: String = "",
//    val title: String,
    val title: AnyRes? = null,
    val func: @Composable () -> Unit = {},
    val perms: List<USER_ROLE> = listOf(USER_ROLE.ANY_USER),
    val funcWithReq: (@Composable (AppRequest) -> Unit)? = null
)

data class SoboBackStackItem(val route: SoboRoute, val req: AppRequest? = null)

fun filterRoutesForMenuWithoutUser(
    routesForMenu: List<String>,
    allRoutes: List<SoboRoute>,
): List<SoboRoute> {
    var routesOut = allRoutes.filter {
        if (routesForMenu.contains(it.handle)) {
            true
        } else {
            false
        }
    }

    return routesOut
}

fun filterRoutesByUserStatus(
    routesForMenu: List<String>,
    allRoutes: List<SoboRoute>,
    isLoggedIn: Boolean, isAdmin: Boolean
): List<SoboRoute> {
    var routesOut = allRoutes.filter {
        if (routesForMenu.contains(it.handle)) {
            if ((isLoggedIn) && (isAdmin)) {
                (it.perms.contains(USER_ROLE.ADMIN) || it.perms.contains(USER_ROLE.USER) || it.perms.contains(
                    USER_ROLE.ANY_USER
                ))
            } else if (isLoggedIn) {
                (it.perms.contains(USER_ROLE.USER) || it.perms.contains(USER_ROLE.ANY_USER))
            } else {
                (it.perms.contains(USER_ROLE.ANON_ONLY) || it.perms.contains(USER_ROLE.ANY_USER))
            }
        } else {
            false
        }
    }

    return routesOut
}


object SoboRouter {
    var routes: List<SoboRoute> = emptyList()
    var routeHandleLoggedInUserHome: String = ""
    var routeHandleAnonymousUserHome: String = ""
    var routeHandleWelcome: String = ""

    var routeHandleLogin: String = ""
    var lmbIsLoggedIn: () -> Boolean = { false }
    var lmbIsLoggedInAdmin: () -> Boolean = { false }
    var backStack = mutableListOf<SoboBackStackItem>()
    var authUsed = true

    fun initRouter(
        routes: List<SoboRoute> = emptyList(),
        routeHandleAnonymousUserHome: String = "",
        routeHandleLoggedInUserHome: String = "",
        routeHandleWelcome: String = "",
        routeHandleLogin: String = "",
        lmbIsLoggedIn: () -> Boolean = { false },
        lmbIsLoggedInAdmin: () -> Boolean = { false },
        authUsed: Boolean = true,
    ) {

        SoboRouter.routes = routes
        SoboRouter.routeHandleLoggedInUserHome = routeHandleLoggedInUserHome
        SoboRouter.routeHandleAnonymousUserHome = routeHandleAnonymousUserHome
        SoboRouter.routeHandleWelcome = routeHandleWelcome
        SoboRouter.routeHandleLogin = routeHandleLogin
        SoboRouter.lmbIsLoggedIn = lmbIsLoggedIn
        SoboRouter.lmbIsLoggedInAdmin = lmbIsLoggedInAdmin
        SoboRouter.authUsed = authUsed
    }

    private fun getRouteByHandle(handle: String): SoboRoute {
        val route: SoboRoute? = routes.find { it.handle == handle }
        if (route == null) {
            throw NoSuchElementException("the route '$handle' does not exist")
        }

        return route
    }

    fun navigateToWelcome() {
        val targetRoute = getRouteByHandle(routeHandleWelcome)
        setCurrentRoute(targetRoute, null)
    }

    fun navigateToWelcomeIfBackStackEmpty() {
        if (isBackStackEmpty()) {
            println("trying to go to welcome page ....\n")
            navigateToWelcome()
        }
    }

    fun navigateToRouteHandle(routeHandle: String, req: AppRequest? = null) {
        val route: SoboRoute = getRouteByHandle(routeHandle)
        setCurrentRoute(route, req)
    }

    fun navigateToEmpty() {
        val route = SoboRoute()
        setCurrentRoute(route, null, false)
    }

    fun navigateToRoute(route: SoboRoute, req: AppRequest? = null) {
        setCurrentRoute(route, req)
    }

    fun applyPermsToRoute(route: SoboRoute) {
        if (!authUsed) {     // if auth is not used in the app, always proceed
            return
        }

        var loggedIn: Boolean = lmbIsLoggedIn()
        var isAdmin: Boolean = lmbIsLoggedInAdmin()

//        println("====== applyPermsToRoute - LOGGED IN: $loggedIn isAdmin: $isAdmin")

        with(route.perms) {
            when {
                contains(USER_ROLE.ANY_USER) -> {  //  "ANY USER - OK!!!"
                    // always proceed
                    return
                }

                contains(USER_ROLE.ANON_ONLY) -> {
                    // TODO check if user logged in. If yes, redirect to DEFAULT_ROUTE (home)
                    if (loggedIn) { // ANON ONLY - LOGGED IN - NOT OK!!! REDIRECTING TO $routeHandleLoggedInUserHome
                        // redirect to our own home (USER or ADMIN)

                        val targetRoute = getRouteByHandle(routeHandleLoggedInUserHome)
                        setCurrentRoute(targetRoute, null)

                        return
                    } else {     // ANON ONLY - NOT LOGGED IN - OK!!!
                        return
                    }
                }

                contains(USER_ROLE.USER) -> {
                    if (loggedIn) { // ROLE USER - LOGGED IN - OK!!!
                        // if logged in, let's go in - USER or ADMIN
                        return
                    } else {   // ROLE USER - NOT LOGGED IN - NOT OK!!! REDIRECTING TO $routeHandleLogin
                        // redirect to login page
                        val targetRoute = getRouteByHandle(routeHandleLogin)
                        setCurrentRoute(targetRoute)
                        return
                    }
                }

                contains(USER_ROLE.ADMIN) -> {
                    if (isAdmin) {   // ROLE ADMIN - LOGGED IN AND ADMIN - OK!!!

                        // if logged in AND admin, let's go in - ADMIN ONLY
                    } else if (loggedIn) { // ROLE ADMIN - LOGGED IN AND NOT ADMIN - NOT OK!!! REDIRECTING TO $routeHandleLoggedInUserHome
                        val targetRoute = getRouteByHandle(routeHandleLoggedInUserHome)
                        setCurrentRoute(targetRoute)
                        return
                    } else {    // ROLE ADMIN - NOT LOGGED IN - NOT OK! REDIRECTING TO $routeHandleLogin
                        val targetRoute = getRouteByHandle(routeHandleLogin)
                        setCurrentRoute(targetRoute)
                        return
                    }
                }

            }
        }
    }

    var currentRouteHandle: MutableState<String> = mutableStateOf("")
    var currentRoute: MutableState<SoboRoute> =
        mutableStateOf(SoboRoute("", null, {}, emptyList(), null))
    var currentRequest: MutableState<AppRequest?> = mutableStateOf(null)
    var canGoBackRmb: MutableState<Boolean> = mutableStateOf(false)

    private fun setCurrentRoute(
        route: SoboRoute,
        req: AppRequest? = null,
        addToBackStack: Boolean = true
    ) {
        currentRoute.value = route
        currentRouteHandle.value = route.handle
        currentRequest.value = req

        if (addToBackStack) {
            val bsItem = SoboBackStackItem(route, req)
            backStack.add(bsItem)
            canGoBackRmb.value = canGoBack()
        }
    }

    fun getPreviousBackStackItemIfAvailable(): SoboBackStackItem? {
        if (backStack.size > 1) {
            val bsItem = backStack.get(backStack.lastIndex - 1)
            return bsItem
        }

        return null;
    }

    fun canGoBack(): Boolean {
        return (backStack.size > 1)
    }

    fun canCloseAppWithBackButton(): Boolean {
        return (backStack.size < 2)
    }

    fun isBackStackEmpty(): Boolean {
        return (backStack.size < 1)
    }

    fun goBackIfPossible(): Boolean {
        println("\ntrying to go back - goBackIfPossible-0000 - backstack size ${backStack.size}")
        if (canGoBack()) {  // current one is always in backStack
            val bsItemCur = backStack.removeAt(backStack.lastIndex) // removes the CURRENT route
            println("\ntrying to go back - goBackIfPossible-0011 - removed the CURRENT route: '${bsItemCur.route.handle}'")
            val bsItemPrev = backStack.removeAt(backStack.lastIndex) // removes the CURRENT - 1 route

            println("\ntrying to go back - goBackIfPossible-0022 - last item was: ${bsItemPrev.route.handle} size AFTER ${backStack.size}")
            println("\ntrying to go back - goBackIfPossible-0033 - backstack size AFTER ${backStack.size}")
            // set the current route as "back", but don't add to history
            setCurrentRoute(
                bsItemPrev.route,
                bsItemPrev.req,
                addToBackStack = false
            )
            canGoBackRmb.value = canGoBack()
            return true
        }

        canGoBackRmb.value = canGoBack()
        return false
    }

    fun getCurrentRoute(): SoboRoute {
        return currentRoute.value
    }

    fun getCurrentRequest(): AppRequest? {
        return currentRequest.value
    }

    fun isRouteSet(): Boolean {
        return (currentRoute.value.handle != "")
    }

    // auth-enabled methods
    fun navigateToLoggedInUserHome() {    // authUsed == true
        if (!authUsed) {  // not all apps provide log-in mechanisms, some don't need them
            return
        }
        if (lmbIsLoggedIn()) {
//            println("navigateToLoggedInUserHome -- REDIRECTING TO $routeHandleLoggedInUserHome")
            // redirect to our own home (USER or ADMIN)
            val targetRoute = getRouteByHandle(routeHandleLoggedInUserHome)
            setCurrentRoute(targetRoute, null)
        }
    }

    fun navigateToDefaultRoute() {
        if (authUsed) {
            return navigateToLogin()
        } else {
            return navigateToWelcome()
        }
    }

    fun navigateToLogin() {   // authUsed == true
        if (!authUsed) {  // not all apps provide log-in mechanisms, some don't need them
            return
        }
        if (!lmbIsLoggedIn()) {   // navigateToLogin -- REDIRECTING TO $routeHandleLogin
            // redirect to LOGIN page
            val targetRoute = getRouteByHandle(routeHandleLogin)
            setCurrentRoute(targetRoute, null)
        }
    }

}
