package cl.joseramos.android.iplacex_programacion2_examen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppMediciones()
        }
    }
}

@Composable
fun AppMediciones (
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "listado"
    ) {
        composable("listado") {
            ListadoUI(onClickIrAIngreso = {navController.navigate("ingreso")})
        }
        composable("ingreso") {
            IngresoUI(onClickIrAListado = {navController.navigate("listado")})
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ListadoUI(
    onClickIrAIngreso:() -> Unit = {}
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {onClickIrAIngreso()}
            ) {
                Icon(Icons.Filled.Add, "Registrar medidor")
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        content = { padding ->
            Column (
                modifier = Modifier.padding(padding)
            ) {
                Text("Pantalla listado mediciones")
            }
        }
    )


}

val HORIZONAL_PADDING = 20.dp

@Preview(showSystemUi = true)
@Composable
fun IngresoUI(
    onClickIrAListado:() -> Unit = {}
) {
    var codigoMedidor by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var tipoMedidor by remember { mutableStateOf("agua") }
    Scaffold(
        content = { padding ->
            Column (
                modifier = Modifier.padding(padding).fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Registro Medidor",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    ),
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                TextField(
                    label = { Text("Medidor") },
                    value = codigoMedidor,
                    onValueChange = { codigoMedidor = it },
                    modifier = Modifier
                        .padding(horizontal = HORIZONAL_PADDING)
                        .height(56.dp)
                        .fillMaxWidth()
                )
                TextField(
                    label = { Text("Fecha") },
                    value = fecha,
                    onValueChange = { fecha = it },
                    modifier = Modifier
                        .padding(horizontal = HORIZONAL_PADDING)
                        .height(56.dp)
                        .fillMaxWidth()
                )
                Column(
                    modifier = Modifier
                        .padding(horizontal = HORIZONAL_PADDING)
                        .padding(top = 16.dp)
                        .fillMaxWidth()
                        .align(Alignment.Start)
                ) {
                    Text(text = "Medidor de:")
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = tipoMedidor == "agua",
                            onClick = { tipoMedidor = "agua" }
                        )
                        Text(text = "Agua")
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = tipoMedidor == "luz",
                            onClick = { tipoMedidor = "luz" }
                        )
                        Text(text = "Luz")
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = tipoMedidor == "gas",
                            onClick = { tipoMedidor = "gas" }
                        )
                        Text(text = "Gas")
                    }
                }

                Button(
                    onClick = {}
                ) {
                    Text("Registrar medición")
                }
            }
        }
    )

}