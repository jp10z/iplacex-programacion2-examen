package cl.joseramos.android.iplacex_programacion2_examen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.joseramos.android.iplacex_programacion2_examen.ui.IngresoUI
import cl.joseramos.android.iplacex_programacion2_examen.ui.ListadoUI

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // se invoca al composable principal
            AppMediciones()
        }
    }
}

// Composable principal que contiene el navhost que se encarga de manejar
// el routing de la aplicación
@Composable
fun AppMediciones (
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "listado" // UI por defecto
    ) {
        composable("listado") {
            ListadoUI(onClickIrAIngreso = {navController.navigate("ingreso") })
        }
        composable("ingreso") {
            IngresoUI(onClickIrAListado = {navController.navigate("listado")
            // Limpio el historial de navegación para que al presionar el botón atrás no vuelva
            // a la pantalla de registro
            {  popUpTo("listado") { inclusive = true } } })
        }
    }
}