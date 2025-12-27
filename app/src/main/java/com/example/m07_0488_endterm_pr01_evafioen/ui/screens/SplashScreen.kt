package com.example.m07_0488_endterm_pr01_evafioen.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.m07_0488_endterm_pr01_evafioen.R
import com.example.m07_0488_endterm_pr01_evafioen.ui.theme.M070488ENDTERMPR01evafioenTheme
import kotlinx.coroutines.delay

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewSplashScreen() {
    M070488ENDTERMPR01evafioenTheme {
        SplashScreen(onSplashFinished = {})
    }
}

@Composable
fun SplashScreen(onSplashFinished: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(2000)
        onSplashFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_spaceapps),
            contentDescription = "Logo de SpaceApps",
            modifier = Modifier.size(200.dp)
        )
    }
}
