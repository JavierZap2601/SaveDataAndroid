package com.example.misdatos

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.misdatos.ui.theme.MisDatosTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.misdatos.viewmodels.PreferencesViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context: Context = LocalContext.current
            val preferencesViewModel = PreferencesViewModel(context)
            MisDatosTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SettingsView(preferencesViewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsView(preferencesViewModel: PreferencesViewModel){
    var nombre by rememberSaveable {
        mutableStateOf("")
    }

    var edad by rememberSaveable {
        mutableStateOf("")
    }

    var altura by rememberSaveable {
        mutableStateOf("")
    }

    var peso by rememberSaveable {
        mutableStateOf("")
    }

    var hobby by rememberSaveable {
        mutableStateOf("")
    }
    var corrutineScope = rememberCoroutineScope()

    var savedAge = preferencesViewModel.age.collectAsState(initial = 0)
    var savedName = preferencesViewModel.name.collectAsState(initial = "???")
    var savedHeight = preferencesViewModel.height.collectAsState(initial = 0.0)
    var savedWeight = preferencesViewModel.weight.collectAsState(initial = 0.0)
    var savedHobby = preferencesViewModel.hobby.collectAsState(initial = "???")

    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally){
        Text(text="Datos del Usuario")
        Spacer(modifier = Modifier.height(15.dp))

        if(savedName.value == "") {
            OutlinedTextField(value = nombre,
                label = { Text("Nombre") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null
                    )
                },
                onValueChange = {
                    nombre = it
                })
            Spacer(modifier = Modifier.height(10.dp))
        }
        if(savedAge.value == 0) {
            OutlinedTextField(value = edad,
                label = { Text("Edad") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null
                    )
                },
                onValueChange = {
                    edad = it
                })
            Spacer(modifier = Modifier.height(10.dp))
        }
        if(savedHeight.value == 0) {
            OutlinedTextField(value = altura,
                label = { Text("Altura") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null
                    )
                },
                onValueChange = {
                    altura = it
                })
            Spacer(modifier = Modifier.height(10.dp))
        }
        if(savedWeight.value == 0.0) {
            OutlinedTextField(value = peso,
                label = { Text("Peso") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null
                    )
                },
                onValueChange = {
                    peso = it
                })
            Spacer(modifier = Modifier.height(10.dp))
        }
        if(savedHobby.value == "") {
            OutlinedTextField(value = hobby,
                label = { Text("Hobby") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null
                    )
                },
                onValueChange = {
                    hobby = it
                })
            Spacer(modifier = Modifier.height(10.dp))
        }
        if(savedName.value == "" && savedAge.value == 0 && savedHeight.value == 0 && savedWeight.value == 0.0 && savedHobby.value == "") {
            Button(onClick = {
                corrutineScope.launch {
                    if (edad.toIntOrNull() != null && altura.toIntOrNull() != null && peso.toFloatOrNull() != null) {
                        preferencesViewModel.setNameAndAge(
                            nombre,
                            edad.toInt(),
                            altura.toInt(),
                            peso.toFloat(),
                            hobby
                        )
                    }
                }
            }) {
                Text(text = "Guardar Datos")
            }
        }
        Text(text = "Nombre: ${savedName.value}")
        Text(text = "Edad: ${savedAge.value} años")
        Text(text = "Altura: ${savedHeight.value} cm")
        Text(text = "Peso: ${savedWeight.value} kg")
        Text(text = "Hobby: ${savedHobby.value}")

    }
}

@Preview(showBackground = true)
@Composable
fun GreetinPreview(){
    MisDatosTheme{
        val context: Context = LocalContext.current
        val preferencesViewModel = PreferencesViewModel(context)
        SettingsView(preferencesViewModel)
    }
}