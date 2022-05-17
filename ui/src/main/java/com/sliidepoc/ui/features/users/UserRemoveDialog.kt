package com.sliidepoc.ui.features.users

/**
 *
 * @author Hagău Ioan
 * @since 2022.02.03
 */
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.sliidepoc.ui.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UserRemoveDialog(
    userId: Int,
    onExecute: (Int) -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        modifier = Modifier
            .wrapContentHeight()
            .width(350.dp),
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = onCancel,
        confirmButton = {
            TextButton(
                colors = ButtonDefaults.textButtonColors(
                    backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                    contentColor = androidx.compose.material3.MaterialTheme.colorScheme.onPrimary
                ),
                onClick = {
                    onExecute(userId)
                },
                content = { Text(stringResource(id = R.string.button_delete_user)) }
            )
        },
        dismissButton = {
            TextButton(
                colors = ButtonDefaults.textButtonColors(
                    backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                    contentColor = androidx.compose.material3.MaterialTheme.colorScheme.onPrimary
                ),
                onClick = { onCancel() },
                content = { Text(stringResource(id = R.string.button_cancel_dialog)) }
            )
        },
        text = {
            Column(
                modifier = Modifier.padding(top = 5.dp)
            ) {
                Text(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.subtitle1,
                    text = stringResource(R.string.dialog_delete_user_title),
                    color = androidx.compose.material3.MaterialTheme.colorScheme.inverseOnSurface
                )
            }
        }
    )
}