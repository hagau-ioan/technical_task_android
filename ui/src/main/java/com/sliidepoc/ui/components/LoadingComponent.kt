package com.sliidepoc.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.sliidepoc.ui.R
import com.sliidepoc.ui.utils.DataLoadingStates

/**
 *
 *
 * @author Hagău Ioan
 * @since 2022.01.28
 */
@Composable
fun LoadingComponent(
    state: DataLoadingStates,
    content: @Composable () -> Unit
) = when (state) {
    DataLoadingStates.LOADING -> {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.data_loading),
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        }
    }
    DataLoadingStates.ERROR -> {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.data_error),
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
    else -> {
        content()
    }
}
