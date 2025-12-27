package com.example.m07_0488_endterm_pr01_evafioen.ui.screens

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SentimentVeryDissatisfied
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.m07_0488_endterm_pr01_evafioen.data.local.RocketEntity
import com.example.m07_0488_endterm_pr01_evafioen.viewmodel.RocketListState
import com.example.m07_0488_endterm_pr01_evafioen.viewmodel.RocketListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onLogout: () -> Unit,
    onRocketClick: (String) -> Unit
) {
    val application = LocalContext.current.applicationContext as Application
    val viewModel: RocketListViewModel = viewModel(factory = RocketListViewModel.provideFactory(application))
    val state by viewModel.state.collectAsState()
    val searchText by viewModel.searchText.collectAsState()
    val showOnlyActive by viewModel.showOnlyActive.collectAsState()

    Scaffold(
        modifier = Modifier.testTag("home_screen"),
        topBar = {
            TopAppBar(
                title = { Text("SpaceApps - Cohetes") },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = "Cerrar sesión")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            // Search and Filter Section
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { viewModel.filter(it) },
                    label = { Text("Buscar cohete...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Mostrar solo activos", style = MaterialTheme.typography.bodyMedium)
                    Switch(
                        checked = showOnlyActive,
                        onCheckedChange = { viewModel.toggleActiveFilter(it) }
                    )
                }
            }

            // Content Section
            when (val currentState = state) {
                is RocketListState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is RocketListState.Error -> ErrorView(message = currentState.message) { viewModel.loadRockets() }
                is RocketListState.Empty -> EmptyView()
                is RocketListState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().testTag("rocket_list"),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(currentState.rockets) { rocket ->
                            RocketCard(rocket = rocket, onClick = { onRocketClick(rocket.id) })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RocketCard(rocket: RocketEntity, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(modifier = Modifier.height(160.dp)) {
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
                            startY = 300f
                        )
                    )
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(
                    text = rocket.name,
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}
@Composable
fun ErrorView(message: String, onRetry: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(24.dp)) {
            Text(message, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.testTag("error_message"))
            Spacer(Modifier.height(16.dp))
            Button(onClick = onRetry, modifier = Modifier.testTag("retry_button")) {
                Text("Reintentar")
            }
        }
    }
}

@Composable
fun EmptyView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.SentimentVeryDissatisfied,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = Color.Gray
            )
            Spacer(Modifier.height(16.dp))
            Text("Sin resultados", style = MaterialTheme.typography.bodyLarge, color = Color.Gray)
        }
    }
}
