package com.example.hellomobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ProfilMahasiswaScreen()
                }
            }
        }
    }
}

@Composable
fun ProfilMahasiswaScreen() {
    // STATE: data yang menentukan tampilan
    var nama by remember { mutableStateOf("") }
    var nim by remember { mutableStateOf("") }
    var prodi by remember { mutableStateOf("") }
    var tersimpan by remember { mutableStateOf(false) }

    // Validasi sederhana
    val nimError = nim.isNotEmpty() && !nim.all { it.isDigit() }
    val formValid = nama.isNotBlank() && nim.isNotBlank() && !nimError && prodi.isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .statusBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Form Profil Mahasiswa", style = MaterialTheme.typography.headlineSmall)

        // EVENT: onValueChange mengubah state, UI ikut diperbarui (recomposition)
        OutlinedTextField(
            value = nama,
            onValueChange = { nama = it; tersimpan = false },
            label = { Text("Nama") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = nim,
            onValueChange = { nim = it; tersimpan = false },
            label = { Text("NIM") },
            singleLine = true,
            isError = nimError,
            supportingText = { if (nimError) Text("NIM hanya boleh berisi angka") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = prodi,
            onValueChange = { prodi = it; tersimpan = false },
            label = { Text("Program Studi") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { tersimpan = true },
            enabled = formValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Simpan")
        }
        Button(
            onClick = { tersimpan = true },
            enabled = formValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Simpan")
        }

        OutlinedButton(
            onClick = {
                nama = ""
                nim = ""
                prodi = ""
                tersimpan = false
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Reset")
        }

        if (!formValid) {
            Text(
                "Lengkapi semua data dengan benar untuk mengaktifkan tombol Simpan.",
                style = MaterialTheme.typography.bodySmall
            )
        }

        // Kartu profil muncul hanya saat state tersimpan = true
        if (tersimpan) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Profil Mahasiswa", fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    Text("Nama: $nama")
                    Text("NIM: $nim")
                    Text("Program Studi: $prodi")
                }
            }
        }
    }
}