package com.example.m07_0488_endterm_pr01_evafioen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.m07_0488_endterm_pr01_evafioen.data.remote.RetrofitInstance
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ErrorStateAndRetryTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        server.start()
        // Apunta Retrofit a nuestro servidor de prueba
        RetrofitInstance.overrideApiUrl(server.url("/").toString())
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun testErrorState_andRetry_succeeds() {
        // Fase 1: Simular un error de red y verificar la pantalla de error

        // 1. Prepara una respuesta de error del servidor
        server.enqueue(MockResponse().setResponseCode(500))

        // 2. Realiza el login para navegar a la pantalla de cohetes
        composeTestRule.mainClock.advanceTimeBy(2100) // Skip splash screen
        composeTestRule.onNodeWithTag("email_field").performTextInput("admin@lasalle.es")
        composeTestRule.onNodeWithTag("password_field").performTextInput("admin1234")
        composeTestRule.onNodeWithTag("login_button").performClick()

        // 3. Verifica que se muestra la vista de error
        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule
                .onAllNodesWithTag("error_message")
                .fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithText("Error al cargar. Verifica tu conexión").assertIsDisplayed()
        composeTestRule.onNodeWithTag("retry_button").assertIsDisplayed()

        // Fase 2: Simular una respuesta exitosa y verificar la recuperación

        // 4. Prepara una respuesta exitosa del servidor con un cohete falso
        val mockRocketJson = """[
            {
                "id": "falcon9",
                "name": "Falcon 9",
                "country": "United States",
                "description": "A two-stage rocket designed and manufactured by SpaceX.",
                "active": true,
                "flickr_images": ["https://imgur.com/DaCfMsj.jpg"],
                "first_flight": "2010-06-04",
                "cost_per_launch": 50000000.0,
                "success_rate_pct": 98.0,
                "wikipedia": "https://en.wikipedia.org/wiki/Falcon_9"
            }
        ]"""
        server.enqueue(MockResponse().setBody(mockRocketJson).addHeader("Content-Type", "application/json"))

        // 5. Haz clic en el botón de reintentar
        composeTestRule.onNodeWithTag("retry_button").performClick()

        // 6. Verifica que la lista de cohetes se muestra correctamente
        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule
                .onAllNodesWithTag("rocket_list")
                .fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithText("Falcon 9").assertIsDisplayed()
    }
}