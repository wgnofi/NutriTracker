package com.example.foodtracker.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.foodtracker.ui.screens.FoodState
import com.example.foodtracker.ui.screens.FoodViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: FoodViewModel
) {
    var navJob: Job? = null
    val scope = rememberCoroutineScope()
    val foods by viewModel.foods.collectAsStateWithLifecycle()
    Scaffold(topBar = {
        TopAppBar(title = {
            Text("Profile")
        }, navigationIcon = {
            IconButton(onClick = {
                navJob?.cancel()
                navJob = scope.launch {
                    delay(200)
                    navController.popBackStack()
                }
            }) {
                Icon(imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = null)
            }
        })

    }) { paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
        ) {
            LimitsCard()
            Text("History", fontSize = 22.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(start = 16.dp))
            Column(modifier = Modifier.verticalScroll(rememberScrollState()).padding(16.dp)) {
                when(foods) {
                    is FoodState.Success -> {
                        val f = (foods as FoodState.Success).foods
                        f.forEach {
                            ListItem(
                                headlineContent = {
                                    Text(it.name)
                                },
                                supportingContent = {
                                    Text("${it.calories} cal ${it.sugar} g ${it.protein} g")
                                },
                                trailingContent = {
                                    Text(it.date)
                                }
                            )
                            HorizontalDivider()
                        }
                    }
                    else -> {
                        Box(modifier = Modifier.fillMaxSize().wrapContentSize(align = Alignment.Center)) {
                            Text("Empty History")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LimitsCard() {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Limits",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Calories")
                Text(text = "2000 cal")
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Sugar")
                Text(text = "30 grams")
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Protein")
                Text(text = "46 grams")
            }
        }
    }
}