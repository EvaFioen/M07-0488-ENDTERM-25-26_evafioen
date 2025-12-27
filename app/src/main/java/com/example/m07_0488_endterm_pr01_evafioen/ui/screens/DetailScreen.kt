package com.example.m07_0488_endterm_pr01_evafioen.ui.screens

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.m07_0488_endterm_pr01_evafioen.viewmodel.RocketDetailState
import com.example.m07_0488_endterm_pr01_evafioen.viewmodel.RocketDetailViewModel
import java.text.NumberFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    rocketId: String,
    onBackToHome: () -> Unit
) {
    val application = LocalContext.current.applicationContext as Application
    val context = LocalContext.current

    val viewModel: RocketDetailViewModel = viewModel(
        factory = RocketDetailViewModel.provideFactory(application, rocketId)
    )

    val state by viewModel.state.collectAsState()

    Scaffold { innerPadding ->
        val bottomPadding = innerPadding.calculateBottomPadding()

        when (val currentState = state) {
            is RocketDetailState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = bottomPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is RocketDetailState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = bottomPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(currentState.message)
                }
            }
            is RocketDetailState.Success -> {
                val rocket = currentState.rocket
                val scrollState = rememberScrollState()

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(bottom = bottomPadding)
                ) {
                    Box(modifier = Modifier.height(220.dp)) {
                        AsyncImage(
                            model = rocket.imageUrl,
                            contentDescription = "Fotografía del cohete ${rocket.name}",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(Color.Transparent, Color.Black),
                                        startY = 400f
                                    )
                                )
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp, vertical = 20.dp),
                            contentAlignment = Alignment.BottomStart
                        ) {
                            Text(
                                text = rocket.name,
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                    Column(modifier = Modifier.padding(16.dp)) {
                        DetailItem("País", rocket.country)
                        DetailItem("Primer vuelo", rocket.firstFlight)
                        DetailItem(
                            "Coste por lanzamiento",
                            rocket.costPerLaunch?.let { "$" + NumberFormat.getNumberInstance(Locale.US).format(it) }
                        )
                        DetailItem("Tasa de éxito", rocket.successRatePct?.let { "$it%" })

                        Spacer(Modifier.height(16.dp))

                        Text(
                            text = "Descripción",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(rocket.description ?: "Sin descripción.", style = MaterialTheme.typography.bodyLarge)

                        Spacer(Modifier.height(24.dp))

                        // Wikipedia
                        rocket.wikipedia?.let {
                            Button(
                                onClick = {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                                    context.startActivity(intent)
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("Más info (Wikipedia)", modifier = Modifier.padding(vertical = 8.dp))
                            }
                        }
                    }
                }
            }
        }
        IconButton(
            onClick = onBackToHome,
            modifier = Modifier.padding(start = 16.dp, top = 32.dp).background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(100))
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = Color.White)
        }
    }
}
@Composable
fun DetailItem(label: String, value: String?) {
    value?.let {
        Row(modifier = Modifier.padding(vertical = 8.dp)) {
            Text("$label: ", fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.bodyLarge)
            Text(it, style = MaterialTheme.typography.bodyLarge)
        }
    }
}
