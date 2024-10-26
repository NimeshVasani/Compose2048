package org.nimesh.game.presentation.composables

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color

@Composable
fun CustomAlertDialog(
    showDialog: MutableState<Boolean>,
    title: String = "Alert",
    message: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showDialog.value = false
            },
            title = {
                Text(text = title, color = Color.Black)
            },
            text = {
                Text(text = message, color = Color.Gray)
            },
            confirmButton = {
                Button(onClick = {
                    onConfirm()
                    showDialog.value = false
                }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    onDismiss()
                    showDialog.value = false
                }) {
                    Text("Dismiss")
                }
            }
        )
    }
}
