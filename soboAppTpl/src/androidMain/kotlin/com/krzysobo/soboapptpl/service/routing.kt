package com.krzysobo.soboapptpl.service

import android.app.Activity


fun handleAndroidBackButton(activity: Activity?) {
    if(SoboRouter.canGoBack()) {
        println("trying to go back... - IT IS POSSIBLE TO GO BACK.  ${SoboRouter.canGoBack()}")
        SoboRouter.goBackIfPossible()
        println(
            "\ntrying to go back... - ${SoboRouter.getCurrentRoute().handle} " +
                    "--> ${SoboRouter.getPreviousBackStackItemIfAvailable()?.route?.handle ?: "-nope-"} \n"
        )

    }else if (SoboRouter.canCloseAppWithBackButton()) {
        println("trying to go back... - IT IS POSSIBLE TO CLOSE APP WITH BACK BUTTON. QUITTING. ${SoboRouter.canCloseAppWithBackButton()}")
        activity?.finish()
    }
    println(
        "\ntrying to go back... - AFTER - ${SoboRouter.getCurrentRoute().handle} " +
                "--> ${SoboRouter.getPreviousBackStackItemIfAvailable()?.route?.handle ?: "-nope-"} \n"
    )
}
