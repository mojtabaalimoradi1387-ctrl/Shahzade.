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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kotlin.math.max
import kotlin.math.min

private val Panel = Color(0xFF20252B)
private val PanelLight = Color(0xFF343B43)
private val Gold = Color(0xFFD5A84D)
private val Screen = Color(0xFF172A31)
private val Green = Color(0xFF55D68B)
private val Red = Color(0xFFE45C58)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility =
            (android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
                    or android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        setContent { MaterialTheme { ShahzadeApp() } }
    }
}

@Composable
private fun ShahzadeApp() {
    var tempo by remember { mutableIntStateOf(120) }
    var transpose by remember { mutableIntStateOf(0) }
    var playing by remember { mutableStateOf(false) }
    var settings by remember { mutableStateOf(false) }
    var dualKeyboard by remember { mutableStateOf(false) }
    var keyCount by remember { mutableIntStateOf(14) }
    var startNote by remember { mutableStateOf("C") }
    var style by remember { mutableStateOf("LORI 01") }
    var selectedSound by remember { mutableStateOf("Concert Piano") }
    var chord by remember { mutableStateOf("C") }

    Surface(Modifier.fillMaxSize(), color = Color(0xFF111519)) {
        if (settings) {
            SettingsScreen(
                keyCount, { keyCount = it }, startNote, { startNote = it },
                dualKeyboard, { dualKeyboard = it }, onBack = { settings = false }
            )
        } else {
            Column(
                Modifier.fillMaxSize().padding(7.dp)
                    .background(Panel, RoundedCornerShape(14.dp))
                    .border(1.dp, Gold.copy(alpha = .65f), RoundedCornerShape(14.dp))
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text("KORG  •  SHAHZADE", color = Gold, fontWeight = FontWeight.Black, fontSize = 17.sp)
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        StatusLamp("STYLE", playing, Green)
                        StatusLamp("RECORD", false, Red)
                        PanelButton("SETTINGS", Gold, Modifier.width(90.dp)) { settings = true }
                    }
                }
                Row(Modifier.weight(1f).fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                    Column(Modifier.weight(1.05f), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                        Text("SOUND SELECT", color = Gold, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        PanelButton("UPPER 1  •  $selectedSound", PanelLight, Modifier.fillMaxWidth()) {
                            selectedSound = if (selectedSound == "Concert Piano") "Warm Strings" else "Concert Piano"
                        }
                        PanelButton("UPPER 2  •  OFF", PanelLight, Modifier.fillMaxWidth()) {}
                        PanelButton("UPPER 3  •  OFF", PanelLight, Modifier.fillMaxWidth()) {}
                        PanelButton("LOWER  •  Grand Piano", PanelLight, Modifier.fillMaxWidth()) {}
                        Spacer(Modifier.weight(1f))
                        Text("STS", color = Gold, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            (1..4).forEach { PanelButton("$it", PanelLight, Modifier.weight(1f)) {} }
                        }
                    }
                    Column(Modifier.weight(1.35f), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                        Box(Modifier.fillMaxWidth().weight(1f)
                            .background(Brush.verticalGradient(listOf(Color(0xFF29434B), Screen)), RoundedCornerShape(8.dp))
                            .border(1.dp, Color(0xFF6D9294), RoundedCornerShape(8.dp)).padding(9.dp)) {
                            Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
                                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("STYLE: $style", color = Color(0xFFBDE7D8), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    Text("BPM $tempo", color = Gold, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                }
                                Text(selectedSound, color = Color.White, fontSize = 19.sp, fontWeight = FontWeight.Bold)
                                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("CHORD  $chord", color = Color.White, fontSize = 12.sp)
                                    Text("TRANSPOSE  ${if (transpose > 0) "+$transpose" else transpose}", color = Color.White, fontSize = 12.sp)
                                    Text("UPPER  ON", color = Green, fontSize = 12.sp)
                                }
                            }
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                            PanelButton("TEMPO −", PanelLight, Modifier.weight(1f)) { tempo = max(30, tempo - 1) }
                            PanelButton("TEMPO +", PanelLight, Modifier.weight(1f)) { tempo = min(300, tempo + 1) }
                            PanelButton("TAP", PanelLight, Modifier.weight(.7f)) { tempo = (tempo + 2).coerceAtMost(300) }
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                            PanelButton("TRANS −", PanelLight, Modifier.weight(1f)) { transpose = (transpose - 1).coerceIn(-12, 12) }
                            PanelButton("RESET", PanelLight, Modifier.weight(.8f)) { transpose = 0 }
                            PanelButton("TRANS +", PanelLight, Modifier.weight(1f)) { transpose = (transpose + 1).coerceIn(-12, 12) }
                        }
                    }
                    Column(Modifier.weight(1.05f), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                        Text("STYLE CONTROL", color = Gold, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        PanelButton(if (playing) "START / STOP  ■" else "START / STOP  ▶", if (playing) Red else Green, Modifier.fillMaxWidth()) { playing = !playing }
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            PanelButton("INTRO 1", PanelLight, Modifier.weight(1f)) {}
                            PanelButton("ENDING 1", PanelLight, Modifier.weight(1f)) {}
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            PanelButton("FILL 1", PanelLight, Modifier.weight(1f)) {}
                            PanelButton("BREAK", PanelLight, Modifier.weight(1f)) {}
                        }
                        Text("VARIATION", color = Gold, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            (1..4).forEach { n -> PanelButton("VAR $n", PanelLight, Modifier.weight(1f)) {} }
                        }
                        PanelButton("STYLE: $style", PanelLight, Modifier.fillMaxWidth()) {
                            style = if (style == "LORI 01") "BAKHTIARI 01" else "LORI 01"
                        }
                        Spacer(Modifier.weight(1f))
                        PanelButton("DUAL KEYBOARD", Gold, Modifier.fillMaxWidth()) { dualKeyboard = !dualKeyboard }
                    }
                }
                if (dualKeyboard) {
                    KeyboardRow(keyCount, startNote, "UPPER")
                    KeyboardRow(keyCount, startNote, "LOWER")
                } else {
                    KeyboardRow(keyCount, startNote, "KEYBOARD")
                }
                Text("Prototype v0.1 • Audio engine and imported Pa700 resources are not included yet",
                    color = Color.LightGray.copy(alpha = .75f), fontSize = 9.sp, textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth())
            }
        }
    }
}

@Composable
private fun StatusLamp(label: String, on: Boolean, active: Color) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        Box(Modifier.size(8.dp).background(if (on) active else Color.DarkGray, RoundedCornerShape(50)))
        Text(label, color = Color.LightGray, fontSize = 9.sp)
    }
}

@Composable
private fun PanelButton(label: String, tint: Color, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(modifier.heightIn(min = 30.dp).background(
        Brush.verticalGradient(listOf(tint.copy(alpha = .95f), tint.copy(alpha = .55f))),
        RoundedCornerShape(6.dp)
    ).border(1.dp, Color.White.copy(alpha = .18f), RoundedCornerShape(6.dp))
        .clickable { onClick() }.padding(horizontal = 5.dp, vertical = 5.dp),
        contentAlignment = Alignment.Center) {
        Text(label, color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center, maxLines = 2)
    }
}

@Composable
private fun KeyboardRow(count: Int, start: String, label: String) {
    val names = listOf("C", "D", "E", "F", "G", "A", "B")
    val startIndex = names.indexOf(start).coerceAtLeast(0)
    Row(Modifier.fillMaxWidth().height(dualHeight()), horizontalArrangement = Arrangement.spacedBy(1.dp)) {
        repeat(count) { i ->
            val note = names[(startIndex + i) % 7]
            var pressed by remember { mutableStateOf(false) }
            Box(Modifier.weight(1f).fillMaxHeight()
                .background(if (pressed) Color(0xFFFFE5A6) else Color(0xFFF1F0E9), RoundedCornerShape(bottomStart = 4.dp, bottomEnd = 4.dp))
                .border(1.dp, Color(0xFF9A9A92), RoundedCornerShape(bottomStart = 4.dp, bottomEnd = 4.dp))
                .clickable { pressed = !pressed }, contentAlignment = Alignment.BottomCenter) {
                Text(if (i == 0) "$label\n$note" else note, color = Color.DarkGray, fontSize = 8.sp,
                    textAlign = TextAlign.Center, modifier = Modifier.padding(bottom = 3.dp))
            }
        }
    }
}

@Composable
private fun dualHeight() = 43.dp

@Composable
private fun SettingsScreen(
    keyCount: Int, onKeyCount: (Int) -> Unit,
    startNote: String, onStartNote: (String) -> Unit,
    dual: Boolean, onDual: (Boolean) -> Unit, onBack: () -> Unit
) {
    Column(Modifier.fillMaxSize().background(Panel).padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Text("KORG-SHAHZADE  •  SETTINGS", color = Gold, fontWeight = FontWeight.Black, fontSize = 18.sp)
            PanelButton("BACK", Gold, Modifier.width(90.dp), onBack)
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(7.dp)) {
                Text("Keyboard key count", color = Color.White, fontWeight = FontWeight.Bold)
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    listOf(7, 8, 9, 10, 11).forEach { n ->
                        PanelButton(if (keyCount == n) "[$n]" else "$n", if (keyCount == n) Gold else PanelLight, Modifier.weight(1f)) { onKeyCount(n) }
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    listOf(12, 13, 14, 21).forEach { n ->
                        PanelButton(if (keyCount == n) "[$n]" else "$n", if (keyCount == n) Gold else PanelLight, Modifier.weight(1f)) { onKeyCount(n) }
                    }
                }
            }
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(7.dp)) {
                Text("Keyboard start note", color = Color.White, fontWeight = FontWeight.Bold)
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    listOf("C", "D", "E", "F", "G", "A", "B").forEach { n ->
                        PanelButton(if (startNote == n) "[$n]" else n, if (startNote == n) Gold else PanelLight, Modifier.weight(1f)) { onStartNote(n) }
                    }
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Dual keyboard mode", color = Color.White, modifier = Modifier.weight(1f))
                    Switch(checked = dual, onCheckedChange = onDual)
                }
            }
        }
        Text("This is the first interface prototype. More settings and audio features will be added in later development phases.",
            color = Color.LightGray, fontSize = 12.sp)
    }
}
