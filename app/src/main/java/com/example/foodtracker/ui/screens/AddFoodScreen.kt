package com.example.foodtracker.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFoodScreen(
    onNavBack: () -> Unit,
    onAddClick: (name: String, calories: Int, sugar: Int, protein: Int) -> Unit
) {
    var name by rememberSaveable {
        mutableStateOf("")
    }
    var calories by rememberSaveable {
        mutableStateOf("")
    }
    var sugar by rememberSaveable {
        mutableStateOf("")
    }
    var protein by rememberSaveable {
        mutableStateOf("")
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Add Food to track")
                },
                navigationIcon = {
                    IconButton(
                        onClick = onNavBack
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ){ padding ->
        Column(modifier = Modifier.padding(padding)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it }, modifier = Modifier.fillMaxWidth(), label = {
                        Text("Enter the name of the food to track")
                    })
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedTextField(
                        value = calories,
                        onValueChange = { calories = it }, modifier = Modifier.weight(1f),
                        label = {
                            Text("Cal")
                        }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    OutlinedTextField(
                        value = sugar,
                        onValueChange = { sugar = it }, modifier = Modifier.weight(1f),
                        label = {
                            Text("Sugar")
                        }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
                    OutlinedTextField(
                        value = protein,
                        onValueChange = { protein = it }, modifier = Modifier.weight(1f),
                        label = {
                            Text("Protein")
                        },keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
                }
                Text("If not applicable enter 0", fontSize = 12.sp)
                ElevatedButton(
                    onClick = {
                        onAddClick(name, calories.toInt(), sugar.toInt(), protein.toInt())
                    }, modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Add To Track")
                }
            }
        }
    }
}

