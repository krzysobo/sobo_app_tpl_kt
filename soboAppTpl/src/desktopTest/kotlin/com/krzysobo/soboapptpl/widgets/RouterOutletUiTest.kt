package com.krzysobo.soboapptpl.widgets

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import com.krzysobo.soboapptpl.service.AnyRes
import com.krzysobo.soboapptpl.service.AppRequest
import com.krzysobo.soboapptpl.service.LocaleManager
import com.krzysobo.soboapptpl.service.LocaleWorker
import com.krzysobo.soboapptpl.service.LocaleWorkerAppSettingsOnly
import com.krzysobo.soboapptpl.service.SoboRoute
import com.krzysobo.soboapptpl.service.SoboRouter
import com.krzysobo.soboapptpl.service.SoboStringResource
import com.krzysobo.soboapptpl.service.USER_ROLE
import com.krzysobo.soboapptpl.service.anyResText
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Integration of SoboRouter with Compose: routerOutlet() renders whatever route is current,
 * applies the route permissions and re-renders after navigation - on the JVM, without a device.
 */
@OptIn(ExperimentalTestApi::class)
class RouterOutletUiTest {
    private var loggedIn = false
    private lateinit var savedWorker: LocaleWorker

    class ItemRequest(val itemId: String) : AppRequest()

    @Composable
    private fun HomeScreen() {
        Column {
            Text("Home screen", Modifier.testTag("home"))
            Button(onClick = { SoboRouter.navigateToRouteHandle("details", ItemRequest("A-17")) }) {
                Text("Open details")
            }
            Button(onClick = { SoboRouter.navigateToRouteHandle("profile") }) {
                Text("Open profile")
            }
        }
    }

    private val routes = listOf(
        SoboRoute(handle = "home", func = { HomeScreen() }),
        SoboRoute(handle = "login", func = { Text("Login screen") }, perms = listOf(USER_ROLE.ANON_ONLY)),
        SoboRoute(
            handle = "profile",
            func = { Text("Profile screen") },
            perms = listOf(USER_ROLE.USER),
        ),
        SoboRoute(
            handle = "details",
            func = { Text("Details without request") },
            funcWithReq = { req -> Text("Details of ${(req as ItemRequest).itemId}") },
        ),
    )

    @BeforeTest
    fun setUp() {
        loggedIn = false
        savedWorker = LocaleManager.localeWorker
        LocaleManager.localeWorker = LocaleWorkerAppSettingsOnly()
        SoboRouter.backStack.clear()
        SoboRouter.initRouter(
            routes = routes,
            routeHandleAnonymousUserHome = "home",
            routeHandleLoggedInUserHome = "home",
            routeHandleWelcome = "home",
            routeHandleLogin = "login",
            lmbIsLoggedIn = { loggedIn },
            lmbIsLoggedInAdmin = { false },
            authUsed = true,
        )
    }

    @AfterTest
    fun tearDown() {
        SoboRouter.backStack.clear()
        LocaleManager.localeWorker = savedWorker
    }

    @Test
    fun rendersCurrentRoute_andNavigatesWithRequest() = runComposeUiTest {
        SoboRouter.navigateToWelcome()
        setContent { routerOutlet() }

        onNodeWithTag("home").assertIsDisplayed()

        onNodeWithText("Open details").performClick()
        onNodeWithText("Details of A-17").assertIsDisplayed()
        assertEquals(listOf("home", "details"), SoboRouter.backStack.map { it.route.handle })
    }

    @Test
    fun backNavigation_rendersPreviousScreen() = runComposeUiTest {
        SoboRouter.navigateToWelcome()
        setContent { routerOutlet() }

        onNodeWithText("Open details").performClick()
        onNodeWithText("Details of A-17").assertIsDisplayed()

        runOnIdle { SoboRouter.goBackIfPossible() }
        onNodeWithTag("home").assertIsDisplayed()
    }

    @Test
    fun protectedRoute_redirectsAnonymousUserToLogin() = runComposeUiTest {
        SoboRouter.navigateToWelcome()
        setContent { routerOutlet() }

        onNodeWithText("Open profile").performClick()

        onNodeWithText("Login screen").assertIsDisplayed()
        assertEquals("login", SoboRouter.getCurrentRoute().handle)
    }

    @Test
    fun protectedRoute_isShownToLoggedInUser() = runComposeUiTest {
        loggedIn = true
        SoboRouter.navigateToWelcome()
        setContent { routerOutlet() }

        onNodeWithText("Open profile").performClick()

        onNodeWithText("Profile screen").assertIsDisplayed()
    }

    @Test
    fun routeWithRequestHandler_withoutRequest_usesPlainFunc() = runComposeUiTest {
        SoboRouter.navigateToRouteHandle("details")
        setContent { routerOutlet() }

        onNodeWithText("Details without request").assertIsDisplayed()
    }

    @Test
    fun anyResText_rendersPlainTextAndTranslations() = runComposeUiTest {
        LocaleManager.setAppLocaleByLang("pl")
        val translated = AnyRes(SoboStringResource(hashMapOf("en" to "Hi %s", "pl" to "Cześć %s")), "Ola")
        setContent {
            Column {
                Text(anyResText(AnyRes("plain text")))
                Text(anyResText(translated))
                Text("[" + anyResText(null) + "]")
            }
        }

        onNodeWithText("plain text").assertIsDisplayed()
        onNodeWithText("Cześć Ola").assertIsDisplayed()
        onNodeWithText("[]").assertIsDisplayed()
    }
}
