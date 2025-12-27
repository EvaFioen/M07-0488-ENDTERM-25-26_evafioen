package com.example.m07_0488_endterm_pr01_evafioen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.m07_0488_endterm_pr01_evafioen.ui.theme.M070488ENDTERMPR01evafioenTheme
import com.example.m07_0488_endterm_pr01_evafioen.ui.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            M070488ENDTERMPR01evafioenTheme {
                AppNavigation()
            }
        }
    }
}
