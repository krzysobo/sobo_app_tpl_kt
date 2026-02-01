package com.krzysobo.soboapptpl.widgets.menus

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonSkippableComposable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.krzysobo.soboapptpl.pubres.PubRes
import com.krzysobo.soboapptpl.service.AnyRes
import com.krzysobo.soboapptpl.service.SoboRoute
import com.krzysobo.soboapptpl.service.SoboRouter
import com.krzysobo.soboapptpl.service.anyResText
import com.krzysobo.soboapptpl.service.filterRoutesByUserStatus
import com.krzysobo.soboapptpl.service.filterRoutesForMenuWithoutUser
import com.krzysobo.soboapptpl.viewmodel.AppViewModelVM
import com.krzysobo.soboapptpl.widgets.routerOutlet
import kotlinx.coroutines.launch

@Composable
@NonSkippableComposable
fun loginLogoutButton(
    actionLogout: () -> Unit = {},
    isUserLoggedIn: () -> Boolean = { false },
    isUserLoggedInAdmin: () -> Boolean = { false },
    showOnlyLogoutButton: Boolean = true,
) {
    val coroutineScope = rememberCoroutineScope()

    /**
     * login/logout button widget
     */
    Box {
        if (SoboRouter.authUsed) {
            if (isUserLoggedIn()) {
                Button(onClick = {
                    coroutineScope.launch {
                        actionLogout()
                    }
                }) {
                    Text(anyResText(AnyRes(PubRes.string.logout)))
                }
            } else if (!showOnlyLogoutButton) {
                Button(onClick = {
                    SoboRouter.navigateToRouteHandle("login")
                }) {
                    Text(anyResText(AnyRes(PubRes.string.login)))
                }
            }
        }
    }
}

@Composable
fun PageTabsWithOutletAndLogin(
    routesForMenu: List<String>,
    routes: List<SoboRoute>,
    isRefreshComposeRequested: () -> Boolean = { false },
    actionLogout: () -> Unit = {},
    isUserLoggedIn: () -> Boolean = { false },
    isUserLoggedInAdmin: () -> Boolean = { false },

    ) {
//    var selectedTabIndex = remember { mutableStateOf(0) }
    val coroutineScope = rememberCoroutineScope()

    /**
     * -- tabs --
     */

    val routesOut =
        if (SoboRouter.authUsed) {
            filterRoutesByUserStatus(
                routesForMenu, routes,
                isUserLoggedIn(),
                isUserLoggedInAdmin()
            )
        } else {
            filterRoutesForMenuWithoutUser(routesForMenu, routes)
        }

    val modSel = Modifier.drawBehind {
        val borderWidth = 3.dp.toPx()
        drawLine(
            color = Color.White,
            start = Offset(0f, size.height - 6),
            end = Offset(size.width, size.height - 6),
            strokeWidth = borderWidth
        )
    }.padding(4.dp)

    /**
     * TOP-BAR TABS
     */

    if (AppViewModelVM.isMenuShown.value) {
        Row {
            TabRow(
                modifier = Modifier
                    .weight(0.75f),
                selectedTabIndex = 0,
                indicator = {}) {
                routesOut.forEachIndexed { index, obj ->
                    val isSelected = obj.handle == SoboRouter.getCurrentRoute().handle
                    Tab(
                        selected = isSelected,
                        onClick = {
                            SoboRouter.navigateToRoute(obj)
                        },
                        modifier = if (isSelected) modSel else Modifier,
                        text = {
                            Text(
                                text = if (obj.title != null) anyResText(obj.title) else "",
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                    )
                }

                loginLogoutButton(actionLogout, isUserLoggedIn, isUserLoggedInAdmin)
            }

        }
    }

    /**
     * -- selected page CONTENT --
     */
    Column(modifier = Modifier.padding(all = 10.dp)) {
        if (!SoboRouter.isRouteSet()) {
//            println("\n====> Route not sent - navigating to default route \n")
            SoboRouter.navigateToDefaultRoute()
        }

        routerOutlet()

        if (isRefreshComposeRequested()) {
            // this is The Refresher - it may even be an empty string, it must
            // be in reaction to a mutableStateOf changes
//                println("COMPOSE REFRESHED!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
            Text("")
        }

    }

}
