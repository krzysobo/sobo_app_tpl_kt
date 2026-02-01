package com.krzysobo.soboapptpl.viewmodel

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import com.krzysobo.soboapptpl.service.AnyRes
import org.jetbrains.compose.resources.DrawableResource

class AnyImage() {
    private var _resDrawableResource: DrawableResource? = null
    private var _resPainter: Painter? = null
    private var _resVector: ImageVector? = null
    private var _resBitmap: ImageBitmap? = null
    private var _contentDescription: AnyRes? = null

    val resPainter: Painter?
        get() = _resPainter

    val resBitmap: ImageBitmap?
        get() = _resBitmap

    val resDrawableResource: DrawableResource?
        get() = _resDrawableResource

    val resVector: ImageVector?
        get() = _resVector

    val contentDescription: AnyRes?
        get() = _contentDescription

    constructor(
        resDrawableResource: DrawableResource,
        contentDescription: AnyRes? = null,
    ) : this() {
        this._resDrawableResource = resDrawableResource
        this._contentDescription = contentDescription
    }

    constructor(resPainter: Painter, contentDescription: AnyRes? = null) : this() {
        this._resPainter = resPainter
        this._contentDescription = contentDescription
    }

    constructor(resBitmap: ImageBitmap, contentDescription: AnyRes? = null) : this() {
        this._resBitmap = resBitmap
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

    fun isBitmap(): Boolean {
        return _resBitmap != null
    }

    fun isDrawableResource(): Boolean {
        return _resDrawableResource != null
    }
}
