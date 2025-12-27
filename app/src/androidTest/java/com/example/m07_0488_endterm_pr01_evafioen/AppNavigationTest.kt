package com.example.m07_0488_endterm_pr01_evafioen

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.m07_0488_endterm_pr01_evafioen.data.remote.RetrofitInstance
import com.example.m07_0488_endterm_pr01_evafioen.ui.navigation.AppNavigation
import com.example.m07_0488_endterm_pr01_evafioen.ui.theme.M070488ENDTERMPR01evafioenTheme
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppNavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        server.start()
        RetrofitInstance.overrideApiUrl(server.url("/").toString())

        composeTestRule.setContent {
            M070488ENDTERMPR01evafioenTheme { AppNavigation() }
        }
        Thread.sleep(2500) // Esperar a que pase el Splash
    }

    /**
     * Verifica que el login navega a la lista de cohetes.
     */
    @Test
    fun test_login_navega_a_la_lista_correctamente() {
        server.enqueue(MockResponse().setBody("[]"))

        performLogin()

        composeTestRule.onNodeWithText("SpaceApps - Cohetes").assertIsDisplayed()
    }

    /**
     * Verifica el manejo de errores de red y el reintento.
     */
    @Test
    fun test_error_de_red_muestra_error_y_reintento_funciona() {
        // 1. Simular error de red
        server.enqueue(MockResponse().setResponseCode(500))

        // 2. Hacer login para llegar a la pantalla con el error
        performLogin()

        // 3. Comprobar que aparece el mensaje de error
        composeTestRule.onNodeWithText("Error al cargar. Verifica tu conexión").assertIsDisplayed()
        composeTestRule.onNodeWithText("Reintentar").assertIsDisplayed()

        // 4. Preparar una respuesta correcta para el reintento
        val jsonRespuesta = """[{"id":"falcon1","name":"Falcon 1","active":false,"flickr_images":["url"]}]"""
        server.enqueue(MockResponse().setBody(jsonRespuesta))

        // 5. Pulsar en "Reintentar"
        composeTestRule.onNodeWithText("Reintentar").performClick()

        // 6. Comprobar que el error desaparece y se carga la lista
        composeTestRule.onNodeWithText("Falcon 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Error al cargar. Verifica tu conexión").assertDoesNotExist()
    }

    /**
     * Encapsula los pasos para hacer login, evitando código repetido
     * y haciendo los tests más limpios e independientes.
     */
    private fun performLogin() {
        composeTestRule.onNodeWithText("Email").performTextInput("admin@lasalle.es")
        composeTestRule.onNodeWithText("Contraseña").performTextInput("admin1234")
        composeTestRule.onNodeWithText("Iniciar sesión").performClick()
    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}
