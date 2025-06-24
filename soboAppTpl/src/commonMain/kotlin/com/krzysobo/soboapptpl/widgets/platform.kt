package com.krzysobo.soboapptpl.widgets

fun isPlatformAndroid(platformName: String): Boolean {
//    return (getPlatform().name.contains("Android"))
    return (platformName.contains("Android"))
}