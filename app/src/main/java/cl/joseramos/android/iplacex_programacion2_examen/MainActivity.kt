package cl.joseramos.android.iplacex_programacion2_examen

import android.graphics.drawable.Drawable
import android.media.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.joseramos.android.iplacex_programacion2_examen.db.Medicion
import cl.joseramos.android.iplacex_programacion2_examen.db.TipoMedidor
import cl.joseramos.android.iplacex_programacion2_examen.ui.ListaMedicionesViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

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
    onClickIrAIngreso:() -> Unit = {},
    vmListaMediciones: ListaMedicionesViewModel = viewModel(factory = ListaMedicionesViewModel.Factory)
) {

    // Se ejecuta una vez al iniciar el composable
    LaunchedEffect(Unit) {
        vmListaMediciones.obtenerMediciones()
    }

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
                LazyColumn {
                    items(vmListaMediciones.mediciones) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(8.dp),
                                horizontalArrangement = Arrangement.SpaceAround,
                                verticalAlignment = Alignment.CenterVertically,
                            ){
                                // Se utiliza row para mostrar el icono del tipo junto a su nombre
                                Row (
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    IconoTipoMedidor(it)
                                    Text(it.tipo)
                                }
                                // Mostrar codigo del medidor
                                // se utiliza separador de miles como en Chile
                                Text(
                                    NumberFormat.getNumberInstance(
                                        Locale("es", "CL")
                                    ).format(it.codigo))
                                // Mostrar fecha, se convierte a string
                                Text(localDateToString(it.fecha))
                            }
                            // Separación horizontal para separar las mediciones
                            HorizontalDivider(thickness = 1.dp)
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun IconoTipoMedidor(medicion: Medicion) {
    // almacena el color que hace contraste con el fondo, se utiliza para pintar los iconos
    val iconColor = MaterialTheme.colorScheme.onBackground
    // renderiza el icono correspondiente según el tipo almacenado
    when(TipoMedidor.valueOf(medicion.tipo)) {
        TipoMedidor.AGUA -> Image(
            painter = painterResource(id=R.drawable.icono_agua),
            contentDescription = "Icono agua",
            colorFilter = ColorFilter.tint(iconColor) //color según fondo
        )
        TipoMedidor.LUZ -> Image(
            painter = painterResource(id=R.drawable.icono_luz),
            contentDescription = "Icono agua",
            colorFilter = ColorFilter.tint(iconColor) //color según fondo
        )
        TipoMedidor.GAS -> Image(
            painter = painterResource(id=R.drawable.icono_gas),
            contentDescription = "Icono agua",
            colorFilter = ColorFilter.tint(iconColor) //color según fondo
        )
    }
}

fun localDateToString(date: LocalDate, pattern: String = "yyyy-MM-dd"): String {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return date.format(formatter)
}

// constante para indicar cual será el padding de izquierda y derecha del formulario
val HORIZONAL_PADDING = 20.dp

@Preview(showSystemUi = true)
@Composable
fun IngresoUI(
    onClickIrAListado:() -> Unit = {},
    vmListaMediciones: ListaMedicionesViewModel = viewModel(factory = ListaMedicionesViewModel.Factory)
) {
    val contexto = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Se ejecuta una vez al iniciar el composable
    LaunchedEffect(Unit) {
        vmListaMediciones.obtenerMediciones()
    }

    var codigoMedidor by remember { mutableStateOf("") }
    var fechaMedicion by remember { mutableStateOf(SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())) }
    var tipoMedidor by remember { mutableStateOf(TipoMedidor.AGUA.toString()) }
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
                    // Limitar carácteres a solo digitos numéricos
                    // lo que se hace es básicamente sobreescribir el mutableState si todos
                    // los carácteres son numéricos, sino ignora el seteo
                    onValueChange = {
                        if (it.all { char -> char.isDigit() }) {
                            codigoMedidor = it
                        }
                    },
                    modifier = Modifier
                        .padding(horizontal = HORIZONAL_PADDING)
                        .height(56.dp)
                        .fillMaxWidth(),
                )
                TextField(
                    label = { Text("Fecha") },
                    value = fechaMedicion,
                    onValueChange = { fechaMedicion = it },
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
                            selected = tipoMedidor == TipoMedidor.AGUA.toString(),
                            onClick = { tipoMedidor = TipoMedidor.AGUA.toString() }
                        )
                        Text(text = "Agua")
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = tipoMedidor == TipoMedidor.LUZ.toString(),
                            onClick = { tipoMedidor = TipoMedidor.LUZ.toString() }
                        )
                        Text(text = "Luz")
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = tipoMedidor == TipoMedidor.GAS.toString(),
                            onClick = { tipoMedidor = TipoMedidor.GAS.toString() }
                        )
                        Text(text = "Gas")
                    }
                }

                Button(
                    onClick = {
                        vmListaMediciones.insertarMedicion(
                            Medicion(
                                codigo = codigoMedidor.toIntOrNull() ?: 0,
                                fecha = LocalDate.parse(fechaMedicion),
                                tipo = tipoMedidor
                            )
                        )

                    }
                ) {
                    Text("Registrar medición")
                }
            }
        }
    )

}