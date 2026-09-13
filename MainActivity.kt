package com.example.powerquality

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFEAF2F8)
                ) {
                    PowerQualityScreen()
                }
            }
        }
    }
}

@Composable
fun PowerQualityScreen() {

    // Стан показників
    var voltage by remember { mutableStateOf(230f) }
    var frequency by remember { mutableStateOf(50f) }
    var quality by remember { mutableStateOf(95f) }

    // Анімовані значення
    val animatedVoltage by animateFloatAsState(
        targetValue = voltage,
        animationSpec = tween(durationMillis = 700),
        label = "voltageAnimation"
    )

    val animatedFrequency by animateFloatAsState(
        targetValue = frequency,
        animationSpec = tween(durationMillis = 700),
        label = "frequencyAnimation"
    )

    val animatedQuality by animateFloatAsState(
        targetValue = quality,
        animationSpec = tween(durationMillis = 700),
        label = "qualityAnimation"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEAF2F8))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Система контролю\nякості електроенергії",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(25.dp))

        Text(
            text = "Поточні показники мережі",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Перший ряд показників
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            QualityCard(
                title = "Напруга",
                value = animatedVoltage,
                unit = "В"
            )

            Spacer(modifier = Modifier.width(10.dp))

            QualityCard(
                title = "Частота",
                value = animatedFrequency,
                unit = "Гц"
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        // Картка якості
        QualityCard(
            title = "Якість мережі",
            value = animatedQuality,
            unit = "%",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Статус
        val status = when {
            quality >= 90 -> "Стан: НОРМА"
            quality >= 70 -> "Стан: УВАГА"
            else -> "Стан: ПРОБЛЕМА"
        }

        val statusColor = when {
            quality >= 90 -> Color(0xFF2E7D32)
            quality >= 70 -> Color(0xFFF57C00)
            else -> Color(0xFFC62828)
        }

        Text(
            text = status,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = statusColor
        )

        Spacer(modifier = Modifier.height(25.dp))

        // Кнопка оновлення
        Button(
            onClick = {

                // Генерація нових показників
                voltage = Random.nextFloat() * 20f + 220f
                frequency = Random.nextFloat() * 1.5f + 49.25f
                quality = Random.nextFloat() * 25f + 70f

            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Оновити показники",
                fontSize = 17.sp
            )
        }
    }
}

@Composable
fun QualityCard(
    title: String,
    value: Float,
    unit: String,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = title,
                fontSize = 16.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = String.format("%.2f", value),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = unit,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}