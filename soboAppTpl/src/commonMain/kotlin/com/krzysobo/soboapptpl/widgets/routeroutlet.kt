package com.krzysobo.soboapptpl.widgets

import androidx.compose.runtime.Composable
import com.krzysobo.soboapptpl.service.SoboRouter

//import androidx.compose.material.Text

@Composable
fun routerOutlet() {
    var curRoute = SoboRouter.getCurrentRoute()
    var curRequest = SoboRouter.getCurrentRequest()

    SoboRouter.applyPermsToRoute(curRoute)

    if ((curRoute.funcWithReq != null) && (curRequest != null)) {
        curRoute.funcWithReq?.invoke(curRequest)
    } else {
        curRoute.func.invoke()
    }

}