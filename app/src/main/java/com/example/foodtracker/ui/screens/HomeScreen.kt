package com.example.foodtracker.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodtracker.R
import com.example.foodtracker.data.Food
import com.example.foodtracker.notification.showLimitExceededNotification

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    foods: List<Food>,
    foodViewModel: FoodViewModel,
    onProfileClick:() -> Unit,
    onFABClick: () -> Unit
) {
    val context = LocalContext.current

    val (cTargetValue, sTargetValue, pTargetValue) = foodViewModel.getTriplesFromToday(foods)
    var cAnimatedProgress by remember { mutableFloatStateOf(0f) }
    var sAnimatedProgress by remember { mutableFloatStateOf(0f) }
    var pAnimatedProgress by remember { mutableFloatStateOf(0f) }

    val cProgressAnimatable = remember { Animatable(0f) }
    val sProgressAnimatable = remember { Animatable(0f) }
    val pProgressAnimatable = remember { Animatable(0f) }

    val animationDuration = 1000

    LaunchedEffect(cTargetValue, sTargetValue, pTargetValue) {
        cProgressAnimatable.animateTo(
            targetValue = cTargetValue,
            animationSpec = tween(durationMillis = animationDuration)
        )
        cAnimatedProgress = cProgressAnimatable.value

        sProgressAnimatable.animateTo(
            targetValue = sTargetValue,
            animationSpec = tween(durationMillis = animationDuration)
        )
        sAnimatedProgress = sProgressAnimatable.value

        pProgressAnimatable.animateTo(
            targetValue = pTargetValue,
            animationSpec = tween(durationMillis = animationDuration)
        )
        pAnimatedProgress = pProgressAnimatable.value
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text("Yo Foodie", fontWeight = FontWeight.Bold, fontSize = 24.sp)
            }, actions = {
                IconButton(onClick = onProfileClick,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .border(
                            width = 5.dp,
                            color = Color.LightGray,
                            shape = RoundedCornerShape(10.dp)
                        )) {
                    Icon(imageVector = Icons.Default.Person, contentDescription = null)
                }
            } )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onFABClick) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Box(contentAlignment = Alignment.TopStart) {
                    Image(
                        painter = painterResource(R.drawable.food), contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.align(Alignment.TopStart)
                    )
                    Spacer(modifier = Modifier.padding(vertical = 200.dp))
                    Surface(modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth(), shape = RoundedCornerShape(20.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)) {
                            Text("Total Intake Today", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                            Row(modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                ElevatedCard(modifier = Modifier.weight(1f)) {
                                    Column(modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth(),
                                        verticalArrangement = Arrangement.spacedBy(16.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally) {
                                        CircularProgressIndicator(
                                            progress = { cAnimatedProgress },
                                            trackColor = Color.LightGray,
                                            strokeWidth = 5.dp,
                                            strokeCap = StrokeCap.Square,
                                        )
                                        Text("Calories")
                                    }
                                }
                                ElevatedCard(modifier = Modifier.weight(1f)) {
                                    Column(modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth(),
                                        verticalArrangement = Arrangement.spacedBy(16.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally) {
                                        CircularProgressIndicator(
                                            progress = { sAnimatedProgress },
                                            trackColor = Color.LightGray,
                                            strokeWidth = 5.dp,
                                            strokeCap = StrokeCap.Square
                                        )
                                        Text("Sugar")
                                    }
                                }
                                ElevatedCard(modifier = Modifier.weight(1f)) {
                                    Column(modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth(),
                                        verticalArrangement = Arrangement.spacedBy(16.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally) {
                                        CircularProgressIndicator(
                                            progress = { pAnimatedProgress },
                                            trackColor = Color.LightGray,
                                            strokeWidth = 5.dp,
                                            strokeCap = StrokeCap.Square
                                        )
                                        Text("Protein")
                                    }
                                }
                            }
                            Text("We will remind you if anyone exceeds limit", fontSize = 12.sp)
                        }
                    }
                }
                Text("Today", fontSize = 22.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(start = 16.dp))
                if (foods.isNotEmpty()) {
                foods.forEach {
                            FoodCard(it)
                        }
                }
                    else  {
                        Text("Empty, Please add as you eat!", modifier = Modifier
                            .fillMaxWidth().wrapContentWidth(align = Alignment.CenterHorizontally)
                            .padding(16.dp))
                    }
                }
            }
        }
    if (cAnimatedProgress >= 1) {
        showLimitExceededNotification(context, "Calories", (cAnimatedProgress * 2000).toInt(), 2000)
    } else if (sAnimatedProgress >= 1) {
        showLimitExceededNotification(context, "Sugar", (sAnimatedProgress * 30).toInt(), 30)
    } else if (pAnimatedProgress >= 1) {
        showLimitExceededNotification(context, "Protein", (pAnimatedProgress * 46).toInt(), 46)
    }

}

@Composable
fun FoodCard(f: Food) {
    ElevatedCard(modifier = Modifier.fillMaxWidth().padding(16.dp),onClick = {},
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text(f.name, fontSize = 18.sp,fontFamily = FontFamily.Monospace, fontWeight = FontWeight.SemiBold)
            Row(modifier = Modifier.wrapContentWidth(align = Alignment.End)
            , verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Text("${f.calories} cal")
                Text("|")
                Text("${f.sugar} g")
                Text("|")
                Text("${f.protein} g")
            }
        }
    }
}
