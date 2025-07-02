package com.krzysobo.soboapptpl.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.krzysobo.soboapptpl.pubres.PubRes
import com.krzysobo.soboapptpl.service.AnyRes
import com.krzysobo.soboapptpl.service.anyResText


/**
 * username  https://developer.android.com/develop/ui/compose/text/user-input
 */
@Composable
fun LoginWidget(
    value: String = "",
    onValueChanges: (String) -> Unit = {},
    isError: Boolean = false,
    focusManager: FocusManager = LocalFocusManager.current,
    labelText: String = anyResText(AnyRes(PubRes.string.username)),
    placeHolderText: String = anyResText(AnyRes(PubRes.string.your_username)),
    errorText: String = anyResText(AnyRes(PubRes.string.username_required)),
    leadingIconUser: @Composable () -> Unit = {
        Icon(
            Icons.Default.Person,
            contentDescription = ""
        )
    }
) {
    TextFieldWithErrorsKeyboardSettings(
        value = value,
        onValueChanges = onValueChanges,
        modifier = Modifier.padding(all = 10.dp).fillMaxWidth(),
        labelText = labelText,
        placeHolderText = placeHolderText,
        leadingIcon = leadingIconUser,
        isError = isError,
        errorText = errorText,
        focusManager = focusManager
    )
}


@Composable
fun PasswordWidget(
    value: String = "",
    onValueChanges: (String) -> Unit = {},
    isError: Boolean = false,
    focusManager: FocusManager = LocalFocusManager.current,
    labelText: String = anyResText(AnyRes(PubRes.string.password)),
    placeHolderText: String = anyResText(AnyRes(PubRes.string.your_password)),
    errorText: String = anyResText(AnyRes(PubRes.string.password_required)),
    leadingIconPass: @Composable () -> Unit = {
        Icon(
            Icons.Default.Lock,
            contentDescription = ""
        )
    },
    trailingIconPassOnClick: () -> Unit = {},
    isPassVisible: Boolean = false,
    isReadOnly: Boolean = false,
    modifier: Modifier = Modifier.padding(all = 10.dp).fillMaxWidth(),
) {

    val trailingIconPass = @Composable {
        IconButton(
            onClick = trailingIconPassOnClick
        ) {
            Icon(
                if (isPassVisible) Icons.Default.VisibilityOff
                else Icons.Default.Visibility,
                contentDescription = ""
            )
        }
    }

    TextFieldWithErrorsKeyboardSettings(
        value = value,
        onValueChanges = onValueChanges,
        modifier = modifier,
        labelText = labelText,
        placeHolderText = placeHolderText,
        leadingIcon = leadingIconPass,
        trailingIcon = trailingIconPass,
        isError = isError,
        errorText = errorText,
        visualTransformation = if (isPassVisible) VisualTransformation.None
        else PasswordVisualTransformation(),
        focusManager = focusManager,
        readOnly = isReadOnly,
    )

}