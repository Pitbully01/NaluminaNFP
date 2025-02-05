package de.nalumina.naluminanfp

import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputScreen(navController: NavController) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    // Optionen für Temperatur & Zervixschleim
    var isTemperatureEnabled by remember { mutableStateOf(false) }
    var isMucusEnabled by remember { mutableStateOf(false) }

    // Temperaturwerte
    var tens by remember { mutableStateOf(3) }
    var ones by remember { mutableStateOf(6) }
    var decimalTens by remember { mutableStateOf(5) }
    var decimalOnes by remember { mutableStateOf(0) }

    var tempTime by remember { mutableStateOf(getCurrentTime()) }
    var bleeding by remember { mutableStateOf("Keine") }

    // Schmerzen mit Intensität
    var headache by remember { mutableStateOf(false) }
    var headacheIntensity by remember { mutableStateOf(1) }

    var breastPain by remember { mutableStateOf(false) }
    var breastPainIntensity by remember { mutableStateOf(1) }

    var nausea by remember { mutableStateOf(false) }
    var nauseaIntensity by remember { mutableStateOf(1) }

    // Zervixschleim
    var mucusAmount by remember { mutableStateOf("Trocken") }
    var mucusConsistency by remember { mutableStateOf("Klebrig") }
    var mucusTime by remember { mutableStateOf(getCurrentTime()) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Neue NFP-Daten") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Basaltemperatur
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = isTemperatureEnabled,
                    onCheckedChange = { isTemperatureEnabled = it })
                Text("Basaltemperatur erfassen")
            }

            if (isTemperatureEnabled) {
                TemperaturePicker(
                    tens = tens,
                    ones = ones,
                    decimalTens = decimalTens,
                    decimalOnes = decimalOnes,
                    onTensChange = { tens = it },
                    onOnesChange = { ones = it },
                    onDecimalTensChange = { decimalTens = it },
                    onDecimalOnesChange = { decimalOnes = it }
                )

                Text("Ausgewählte Temperatur: $tens$ones,$decimalTens$decimalOnes°C")

                // Temperatur Zeit-Eingabe
                TimePickerField(context, "Zeit der Temperaturmessung", tempTime) { newTime ->
                    tempTime = newTime
                }
            }

            // Blutung
            Text("Blutung:")
            DropdownMenuField(listOf("Keine", "Leicht", "Mittel", "Stark"), bleeding) {
                bleeding = it
            }

            // Schmerzen mit Intensität
            Text("Körperliche Beschwerden:")
            PainIntensityCheckbox(
                "Kopfschmerzen",
                headache,
                headacheIntensity
            ) { checked, intensity ->
                headache = checked
                headacheIntensity = intensity
            }
            PainIntensityCheckbox(
                "Brustspannen",
                breastPain,
                breastPainIntensity
            ) { checked, intensity ->
                breastPain = checked
                breastPainIntensity = intensity
            }
            PainIntensityCheckbox("Übelkeit", nausea, nauseaIntensity) { checked, intensity ->
                nausea = checked
                nauseaIntensity = intensity
            }

            // Zervixschleim
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = isMucusEnabled, onCheckedChange = { isMucusEnabled = it })
                Text("Zervixschleim erfassen")
            }

            if (isMucusEnabled) {
                Text("Zervixschleim - Menge:")
                DropdownMenuField(listOf("Trocken", "Wenig", "Normal", "Viel"), mucusAmount) {
                    mucusAmount = it
                }

                if (mucusAmount != "Trocken") {
                    Text("Zervixschleim - Konsistenz:")
                    DropdownMenuField(
                        listOf("Klebrig", "Cremig", "Wässrig", "Spinnbar"),
                        mucusConsistency
                    ) {
                        mucusConsistency = it
                    }
                }

                // Zervixschleim Zeit-Eingabe
                TimePickerField(context, "Zeit der Zervixschleim-Bewertung", mucusTime) { newTime ->
                    mucusTime = newTime
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { navController.navigate(Screen.Home.route) }) {
                Text("Speichern und zurück")
            }
        }
    }
}

@Composable
fun TemperaturePicker(
    tens: Int, ones: Int, decimalTens: Int, decimalOnes: Int,
    onTensChange: (Int) -> Unit, onOnesChange: (Int) -> Unit,
    onDecimalTensChange: (Int) -> Unit, onDecimalOnesChange: (Int) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom // Alle Elemente nach unten ausrichten
    ) {
        NumberPicker(range = 3..4, selected = tens, onValueChange = onTensChange)
        NumberPicker(range = 0..9, selected = ones, onValueChange = onOnesChange)

        Text(
            ",",
            modifier = Modifier
                .padding(bottom = 0.dp) // Komma tiefer setzen
                .align(Alignment.CenterVertically),
            style = MaterialTheme.typography.headlineMedium
        )

        NumberPicker(range = 0..9, selected = decimalTens, onValueChange = onDecimalTensChange)
        NumberPicker(range = 0..9, selected = decimalOnes, onValueChange = onDecimalOnesChange)

        Text(
            "°C",
            modifier = Modifier
                .padding(top = 16.dp) // °C tiefer setzen
                .align(Alignment.CenterVertically),
            style = MaterialTheme.typography.headlineMedium
        )
    }
}


// Universeller Zahlen-Picker für jede einzelne Stelle (Endlos-Scroll mit automatischer Zentrierung + Einrasten)
@Composable
fun NumberPicker(range: IntRange, selected: Int, onValueChange: (Int) -> Unit) {
    val list =
        (range.toList() + range.toList() + range.toList()) // Erzeugt Endlos-Scroll-Effekt durch Wiederholung
    val lazyListState =
        rememberLazyListState(initialFirstVisibleItemIndex = list.size / 3 + selected)

    LaunchedEffect(lazyListState.firstVisibleItemIndex) {
        val centeredIndex = lazyListState.firstVisibleItemIndex + 1
        val newValue = list[centeredIndex % list.size]
        if (newValue in range) onValueChange(newValue)
    }

    Box(
        modifier = Modifier
            .height(160.dp)
            .width(60.dp)
    ) {
        LazyColumn(
            state = lazyListState,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            itemsIndexed(list) { index, value ->
                Text(
                    text = value.toString(),
                    style = MaterialTheme.typography.headlineMedium,
                    color = if (index == lazyListState.firstVisibleItemIndex + 1) MaterialTheme.colorScheme.primary else Color.Gray,
                    modifier = Modifier
                        .padding(8.dp)
                )
            }
        }

        // Einrast-Effekt: Erzwingt die Zentrierung auf die nächstgelegene Position
        LaunchedEffect(lazyListState.isScrollInProgress) {
            if (!lazyListState.isScrollInProgress) {
                val centeredIndex = lazyListState.firstVisibleItemIndex
                lazyListState.animateScrollToItem(centeredIndex)
            }
        }
    }
}


// Hilfsfunktion für die aktuelle Uhrzeit als Standardwert
fun getCurrentTime(): String {
    val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    return dateFormat.format(Date())
}

// Hilfsfunktion für die Zeitauswahl
@Composable
fun TimePickerField(
    context: Context,
    label: String,
    time: String,
    onTimeSelected: (String) -> Unit
) {
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    OutlinedButton(onClick = {
        TimePickerDialog(
            context,
            { _, selectedHour, selectedMinute ->
                onTimeSelected(String.format("%02d:%02d", selectedHour, selectedMinute))
            },
            hour, minute, true
        ).show()
    }) {
        Text("$label: $time")
    }
}

// Schmerzen mit Intensität (Checkbox oben, Slider darunter)
@Composable
fun PainIntensityCheckbox(
    text: String,
    checked: Boolean,
    intensity: Int,
    onCheckedChange: (Boolean, Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = checked,
                onCheckedChange = {
                    onCheckedChange(it, if (it) 5 else 1) // Standardwert 5, wenn aktiviert
                }
            )
            Text(text)
        }
        if (checked) {
            Slider(
                value = intensity.toFloat(),
                onValueChange = { onCheckedChange(true, it.toInt()) },
                valueRange = 1f..10f,
                steps = 8,
                modifier = Modifier.fillMaxWidth()
            )
            Text("$intensity/10", modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }
}


// Hilfsfunktion für Dropdown-Menüs
@Composable
fun DropdownMenuField(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        OutlinedButton(onClick = { expanded = true }) {
            Text(selectedOption)
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(text = { Text(option) }, onClick = {
                    onOptionSelected(option)
                    expanded = false
                })
            }
        }
    }
}