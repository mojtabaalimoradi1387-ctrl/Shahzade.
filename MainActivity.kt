package com.shahzade.arranger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val BodyColor = Color(0xFF292326)
private val PanelColor = Color(0xFF171719)
private val RedColor = Color(0xFFB91C1C)
private val GoldColor = Color(0xFFD2B16D)
private val ScreenColor = Color(0xFF101D38)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = 5894 or 1024 or 512
        requestedOrientation =
            android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        setContent {
            MaterialTheme {
                ArrangerScreen()
            }
        }
    }
}

@Composable
fun ArrangerScreen() {
    var playing by remember { mutableStateOf(false) }
    var tempo by remember { mutableIntStateOf(120) }
    var transpose by remember { mutableIntStateOf(0) }
    var style by remember { mutableStateOf("LORI 01") }
    var selectedSound by remember { mutableStateOf("GRAND PIANO") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(5.dp)
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF514347), BodyColor, Color(0xFF171416))
                    ),
                    RoundedCornerShape(12.dp)
                )
                .border(2.dp, Color(0xFF65565A), RoundedCornerShape(12.dp))
                .padding(7.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(0.85f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("KORG", color = Color.White, fontSize = 23.sp,
                    fontWeight = FontWeight.Black)
                Text("Pa700 STYLE", color = GoldColor, fontSize = 10.sp)
                Spacer(Modifier.height(12.dp))
                PanelButton("MASTER VOLUME", Color.DarkGray) {}
                PanelButton("BALANCE", Color.DarkGray) {}
                PanelButton("MODE", PanelColor) {}
                PanelButton("SONG", PanelColor) {}
            }

            Column(
                modifier = Modifier.weight(2.5f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("KORG-SHAHZADE", color = GoldColor,
                    fontSize = 18.sp, fontWeight = FontWeight.Black)
                Spacer(Modifier.height(5.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(ScreenColor, RoundedCornerShape(8.dp))
                        .border(3.dp, Color(0xFF85858A), RoundedCornerShape(8.dp))
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("STYLE PLAY", color = GoldColor, fontSize = 12.sp)
                        Text("TEMPO $tempo", color = Color.White, fontSize = 12.sp)
                        Text("READY", color = Color.Green, fontSize = 12.sp)
                    }
                    Spacer(Modifier.height(9.dp))
                    Text(style, color = Color.White, fontSize = 23.sp,
                        fontWeight = FontWeight.Bold)
                    Text(selectedSound, color = Color(0xFFB8D9FF), fontSize = 13.sp)
                    Spacer(Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        ScreenButton("STYLE") { style = "LORI 01" }
                        ScreenButton("SOUND") { selectedSound = "GRAND PIANO" }
                        ScreenButton("MEDIA") { selectedSound = "MEDIA" }
                        ScreenButton("GLOBAL") { selectedSound = "GLOBAL" }
                    }
                }

                Spacer(Modifier.height(6.dp))
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    PanelButton("INTRO", PanelColor) {}
                    PanelButton("VARIATION", PanelColor) {}
                    PanelButton("FILL", PanelColor) {}
                    PanelButton("BREAK", PanelColor) {}
                    PanelButton("ENDING", PanelColor) {}
                }
            }

            Column(
                modifier = Modifier.weight(0.9f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("VALUE", color = GoldColor, fontSize = 12.sp)
                Spacer(Modifier.height(8.dp))
                PanelButton("SELECT", Color.DarkGray) {}
                Spacer(Modifier.height(12.dp))
                Text("TEMPO", color = Color.White, fontSize = 12.sp)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    PanelButton("−", PanelColor) {
                        tempo = (tempo - 1).coerceAtLeast(30)
                    }
                    PanelButton("+", PanelColor) {
                        tempo = (tempo + 1).coerceAtMost(300)
                    }
                }
                Text("$tempo BPM", color = GoldColor, fontSize = 12.sp)
                Spacer(Modifier.height(8.dp))
                Text("TRANSPOSE", color = Color.White, fontSize = 11.sp)
                Row {
                    PanelButton("−", PanelColor) {
                        transpose = (transpose - 1).coerceAtLeast(-12)
                    }
                    PanelButton("+", PanelColor) {
                        transpose = (transpose + 1).coerceAtMost(12)
                    }
                }
                Text("$transpose", color = GoldColor)
            }
        }

        Row(
            Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(Color(0xFF111111)),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            PanelButton("SETTINGS", Color.DarkGray) {}
            PanelButton("START / STOP", if (playing) RedColor else PanelColor) {
                playing = !playing
            }
            Text(
                if (playing) "PLAYING" else "STOPPED",
                color = if (playing) Color.Green else Color.LightGray,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            PanelButton("SYNCHRO", PanelColor) {}
            PanelButton("TAP", PanelColor) {}
        }

        PianoKeyboard()
    }
}

@Composable
fun PanelButton(
    label: String,
    color: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(2.dp)
            .defaultMinSize(minWidth = 42.dp, minHeight = 29.dp)
            .background(
                Brush.verticalGradient(
                    listOf(color.copy(alpha = 0.95f), Color.Black)
                ),
                RoundedCornerShape(5.dp)
            )
            .border(1.dp, Color(0xFF777777), RoundedCornerShape(5.dp))
            .clickable { onClick() }
            .padding(horizontal = 5.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            label,
            color = Color.White,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ScreenButton(label: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .background(Color(0xFF263F68), RoundedCornerShape(4.dp))
            .border(1.dp, Color(0xFF6686B5), RoundedCornerShape(4.dp))
            .clickable { onClick() }
            .padding(horizontal = 9.dp, vertical = 7.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(label, color = Color.White, fontSize = 9.sp)
    }
}

@Composable
fun PianoKeyboard() {
    val whiteKeys = listOf(
        "C", "D", "E", "F", "G", "A", "B",
        "C", "D", "E", "F", "G", "A", "B"
    )
    var pressedKey by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(105.dp)
            .background(Color.Black)
            .padding(2.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        whiteKeys.forEach { key ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(
                        if (pressedKey == key) Color(0xFFFFDFA0)
                        else Color(0xFFF3F0E9),
                        RoundedCornerShape(bottomStart = 4.dp, bottomEnd = 4.dp)
                    )
                    .border(1.dp, Color.DarkGray)
                    .clickable {
                        pressedKey = if (pressedKey == key) "" else key
                    },
                contentAlignment = Alignment.BottomCenter
            ) {
                Text(
                    key,
                    color = Color.DarkGray,
                    fontSize = 10.sp,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
        }
    }
}
