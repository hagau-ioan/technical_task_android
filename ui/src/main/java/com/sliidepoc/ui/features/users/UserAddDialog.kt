package com.sliidepoc.ui.features.users

/**
 *
 * @author Hagău Ioan
 * @since 2022.02.03
 */
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.AlertDialog
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.sliidepoc.common.utils.formater.StringUtils
import com.sliidepoc.ui.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UserAddDialog(
    onCancel: () -> Unit,
    onSave: (String, String) -> Unit
) {
    var nameField by remember { mutableStateOf(StringUtils.EMPTY_STRING) }
    var emailField by remember { mutableStateOf(StringUtils.EMPTY_STRING) }

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
                    if (nameField.isNotEmpty() && emailField.isNotEmpty()) {
                        onSave(
                            nameField,
                            emailField
                        )
                    }
                },
                content = { Text(stringResource(id = R.string.button_add_user)) }
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
                    text = stringResource(R.string.dialog_add_user_title),
                    color = androidx.compose.material3.MaterialTheme.colorScheme.inverseOnSurface
                )
                OutlinedTextField(
                    modifier = Modifier.padding(top = 10.dp),
                    label = { Text(stringResource(R.string.edittext_name)) },
                    value = nameField,
                    isError = false,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = androidx.compose.material3.MaterialTheme.colorScheme.primary
                    ),
                    onValueChange = {
                        nameField = it
                    }
                )
                Spacer(modifier = Modifier.height(5.dp))
                OutlinedTextField(
                    label = { Text(stringResource(R.string.edittext_email)) },
                    value = emailField,
                    isError = false,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = androidx.compose.material3.MaterialTheme.colorScheme.primary
                    ),
                    onValueChange = {
                        emailField = it
                    }
                )
            }
        }
    )
}
