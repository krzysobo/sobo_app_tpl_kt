package com.krzysobo.soboapptpl.widgets

//import com.krzysobo.kt0.getPlatform

fun isPlatformAndroid(platformName: String): Boolean {
//    return (getPlatform().name.contains("Android"))
    return (platformName.contains("Android"))
}