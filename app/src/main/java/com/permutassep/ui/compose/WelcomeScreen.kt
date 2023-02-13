package com.permutassep.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.lalongooo.permutassep.R


@Preview(showSystemUi = true)
@Composable
fun WelcomeScreen() {
    Column(Modifier.fillMaxSize()) {
        Text(
                text = stringResource(R.string.app_login_sign_up_already_have_account),
                style = MaterialTheme.typography.headlineMedium
        )
        Text(
                text = stringResource(R.string.app_login_sign_up_log_in),
                style = MaterialTheme.typography.bodyMedium
        )
        Text(
                text = stringResource(R.string.app_login_sign_up_want_to_register),
                style = MaterialTheme.typography.headlineMedium
        )
        Text(
                text = stringResource(R.string.app_login_sign_up_want_to_register_cta),
                style = MaterialTheme.typography.bodyMedium
                // style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
                onClick = { /* Handle click */ },
                Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.app_login_sign_up_want_to_register_cta))
        }
    }
}