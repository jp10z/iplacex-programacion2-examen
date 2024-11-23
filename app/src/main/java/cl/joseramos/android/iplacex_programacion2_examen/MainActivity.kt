package cl.joseramos.android.iplacex_programacion2_examen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
        startDestination = "listadoMediciones"
    ) {
        composable("listadoMediciones") {
            PageListadoMedicionesUI(navController)
        }
        composable("formularioIngresoMedicion") {
            PageFormularioIngresoMedicionUI()
        }
    }
}

@Composable
fun PageListadoMedicionesUI(navController: NavHostController) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {navController.navigate("formularioIngresoMedicion")}
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

@Composable
fun PageFormularioIngresoMedicionUI() {
    Scaffold(
        content = { padding ->
            Column (
                modifier = Modifier.padding(padding)
            ) {
                Text("Pantalla formulario ingreso medicion")
            }
        }
    )

}