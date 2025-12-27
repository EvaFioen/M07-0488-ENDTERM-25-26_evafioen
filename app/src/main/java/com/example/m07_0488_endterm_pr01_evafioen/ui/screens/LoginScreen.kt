package com.example.m07_0488_endterm_pr01_evafioen.ui.screens

import android.content.Intent
import android.util.Patterns
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.example.m07_0488_endterm_pr01_evafioen.ui.theme.M070488ENDTERMPR01evafioenTheme
import kotlinx.coroutines.launch

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewLoginScreen() {
    M070488ENDTERMPR01evafioenTheme {
        LoginScreen(onLoginSuccess = {})
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit
) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    fun validateForm(): Boolean {
        var isValid = true

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailError = "Introduce un email válido"
            isValid = false
        } else {
            emailError = null
        }

        if (password.isBlank()) {
            passwordError = "La contraseña no puede estar vacía"
            isValid = false
        } else if (password.length < 6) {
            passwordError = "La contraseña debe tener al menos 6 caracteres"
            isValid = false
        } else {
            passwordError = null
        }

        return isValid
    }

    fun onLoginClick() {
        if (!validateForm()) {
            return
        }
        if (email == "admin@lasalle.es" && password == "admin1234") {
            onLoginSuccess()
        } else {
            scope.launch {
                snackbarHostState.showSnackbar("Credenciales incorrectas")
            }
        }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "Iniciar sesión",
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("email_field"),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
                if (emailError != null) {
                    Text(
                        text = emailError!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("password_field"),
                    visualTransformation = PasswordVisualTransformation()
                )
                if (passwordError != null) {
                    Text(
                        text = passwordError!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(
                    onClick = {
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            "https://lasallefp.com/contactar/".toUri()
                        )
                        context.startActivity(intent)
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("He olvidado mis datos de acceso")
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { onLoginClick() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("login_button")
                ) {
                    Text("Iniciar sesión")
                }
            }
        }
    }

}