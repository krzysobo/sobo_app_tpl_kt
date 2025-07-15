package com.krzysobo.soboapptpl.widgets

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.krzysobo.soboapptpl.pubres.PubRes
import com.krzysobo.soboapptpl.service.AnyRes
import com.krzysobo.soboapptpl.service.anyResText

data class DialogVisibility(
    var helpAboutOpen: MutableState<Boolean>,
    var helpContactOpen: MutableState<Boolean>
)

@Composable
fun HelpAboutDialog(visible: Boolean, dialogText: String = "", onDismissRequest: () -> Unit) {
    ToggleableDialog(
        visible,
        onDismissRequest = onDismissRequest,
        onConfirmation = onDismissRequest,
        dialogTitle = anyResText(AnyRes(PubRes.string.about)),
        dialogText = dialogText
    )
}

@Composable
fun HelpContactDialog(visible: Boolean, dialogText: String = "", onDismissRequest: () -> Unit) {
    ToggleableDialog(
        visible,
        onDismissRequest = onDismissRequest,
        onConfirmation = onDismissRequest,
        dialogTitle = anyResText(AnyRes(PubRes.string.contact_info)),
        dialogText = dialogText,
    )

}

@Composable
fun ToggleableDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    confirmButtonText: String = anyResText(AnyRes(PubRes.string.ok)),
    dismissButtonText: String? = null
) {
    var confirmBtn: (@Composable () -> Unit)?
    var dismissBtn: (@Composable () -> Unit)? = null;

    if ((dismissButtonText != null) && (dismissButtonText != "")) {
        dismissBtn = @Composable {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(dismissButtonText)
            }
        }
    }

    confirmBtn = @Composable {
        TextButton(
            onClick = {
                onConfirmation()
            }
        ) {
            Text(confirmButtonText)
        }
    }

    if (visible) {
        AlertDialog(
            title = {
                Text(text = dialogTitle)
            },
            text = {
                Text(text = dialogText)
            },
            onDismissRequest = {
                onDismissRequest()
            },
            confirmButton = confirmBtn,
//            dismissButton = null,
            dismissButton = dismissBtn,
        )
    }
}
