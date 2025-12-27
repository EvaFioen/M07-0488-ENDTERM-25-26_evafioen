package com.example.m07_0488_endterm_pr01_evafioen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import org.junit.Rule
import org.junit.Test

class LoginNavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testValidLogin_navigatesToRocketList() {
        // 1. Espera a que la SplashScreen termine y aparezca la LoginScreen.
        // Usamos waitUntil para esperar de forma robusta a que el campo de email sea visible,
        // lo que nos asegura que la pantalla de login ha cargado.
        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule
                .onAllNodesWithTag("email_field")
                .fetchSemanticsNodes().isNotEmpty()
        }

        // 2. Introduce las credenciales válidas
        composeTestRule.onNodeWithTag("email_field").performTextInput("admin@lasalle.es")
        composeTestRule.onNodeWithTag("password_field").performTextInput("admin1234")

        // 3. Haz clic en el botón de login
        composeTestRule.onNodeWithTag("login_button").performClick()

        // 4. Verifica que se ha navegado a la pantalla de la lista de cohetes
        // Comprobamos que el Scaffold de HomeScreen, que tiene el testTag "home_screen", está visible.
        composeTestRule.onNodeWithTag("home_screen").assertIsDisplayed()
    }
}