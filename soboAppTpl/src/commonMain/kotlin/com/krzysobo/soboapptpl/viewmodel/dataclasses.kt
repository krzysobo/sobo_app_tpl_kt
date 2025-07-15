package com.krzysobo.soboapptpl.viewmodel

import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import com.krzysobo.soboapptpl.service.AnyRes
import com.krzysobo.soboapptpl.widgets.LangOpt
import org.jetbrains.compose.resources.DrawableResource


data class AppItem(
    val title: AnyRes? = null,
    val image: AnyImage? = null,
    val focusRequester: FocusRequester? = null,   // ADDED HERE
)


class AnyImage() {
    //    private var resImage: Image? = null
    private var _resDrawable: DrawableResource? = null
    private var _resPainter: Painter? = null
    private var _resVector: ImageVector? = null
    private var _contentDescription: AnyRes? = null

    val resPainter: Painter?
        get() = _resPainter

    val resDrawable: DrawableResource?
        get() = _resDrawable

    val resVector: ImageVector?
        get() = _resVector

    val contentDescription: AnyRes?
        get() = _contentDescription

    constructor(resDrawable: DrawableResource, contentDescription: AnyRes? = null) : this() {
        this._resDrawable = resDrawable
        this._contentDescription = contentDescription
    }

    constructor(resPainter: Painter, contentDescription: AnyRes? = null) : this() {
        this._resPainter = resPainter
        this._contentDescription = contentDescription
    }

    constructor(resVector: ImageVector, contentDescription: AnyRes? = null) : this() {
        this._resVector = resVector
        this._contentDescription = contentDescription
    }

    fun isVector(): Boolean {
        return _resVector != null
    }

    fun isPainter(): Boolean {
        return _resPainter != null
    }

    fun isDrawableResource(): Boolean {
        return _resDrawable != null
    }
}

data class LangWithFlag(val langOpt: LangOpt, val image: AnyImage)