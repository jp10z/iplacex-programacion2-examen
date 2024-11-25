package cl.joseramos.android.iplacex_programacion2_examen.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.joseramos.android.iplacex_programacion2_examen.R
import cl.joseramos.android.iplacex_programacion2_examen.db.Medicion
import cl.joseramos.android.iplacex_programacion2_examen.db.TipoMedidor
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

// Este composable representa la pantalla que muestra el listado de mediciones
@Preview(showSystemUi = true)
@Composable
fun ListadoUI(
    onClickIrAIngreso:() -> Unit = {},
    vmListaMediciones: ListaMedicionesViewModel = viewModel(factory = ListaMedicionesViewModel.Factory)
) {

    // Recargo la lista de mediciones cuándo inicio el composable
    LaunchedEffect(Unit) {
        vmListaMediciones.obtenerMediciones()
    }

    Scaffold(
        // Agrego el floating icon mostrado en la zona inferior derecha de la pantalla
        // Este al hacer click llama a la función onClickIrAIngreso que se pasa desde el padre
        // para navegar a la pantalla de creación mediante callback
        floatingActionButton = {
            FloatingActionButton(
                onClick = {onClickIrAIngreso()}
            ) {
                Icon(Icons.Filled.Add, "Registrar medición")
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
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 24.dp)
                                    .padding(vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceAround,
                                verticalAlignment = Alignment.CenterVertically,
                            ){
                                // Se utiliza row para agrupar el icono y el texto del tipo
                                // de esta manera siempre quedarán juntos
                                Row (
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.weight(2f)
                                ) {
                                    // Obtengo el icono y el texto del tipo desde sub-composables
                                    IconoTipoMedidor(it)
                                    TextoTipoMedidor(it)
                                }
                                // Mostrar codigo del medidor
                                // se utiliza separador de miles como en Chile
                                Row(
                                    horizontalArrangement = Arrangement.End,
                                    modifier = Modifier.weight(2f)
                                ){
                                    Text(
                                        NumberFormat.getNumberInstance(
                                            Locale("es", "CL")
                                        ).format(it.valor)
                                    )
                                }

                                // Mostrar fecha, se convierte a string
                                Row(
                                    horizontalArrangement = Arrangement.End,
                                    modifier = Modifier.weight(3f)
                                ){
                                    Text(localDateToString(it.fecha))
                                }
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
            painter = painterResource(id= R.drawable.icono_agua),
            contentDescription = "Icono agua",
            colorFilter = ColorFilter.tint(iconColor) //color según fondo
        )
        TipoMedidor.LUZ -> Image(
            painter = painterResource(id= R.drawable.icono_luz),
            contentDescription = "Icono agua",
            colorFilter = ColorFilter.tint(iconColor) //color según fondo
        )
        TipoMedidor.GAS -> Image(
            painter = painterResource(id= R.drawable.icono_gas),
            contentDescription = "Icono agua",
            colorFilter = ColorFilter.tint(iconColor) //color según fondo
        )
    }
}

@Composable
fun TextoTipoMedidor(medicion: Medicion) {
    // almacenar contexto para leer los string de traducción
    val contexto = LocalContext.current
    // retornar composable texto con la traducción lista
    when(TipoMedidor.valueOf(medicion.tipo)) {
        TipoMedidor.AGUA -> Text(contexto.getString(R.string.text_tipo_agua))
        TipoMedidor.LUZ -> Text(contexto.getString(R.string.text_tipo_luz))
        TipoMedidor.GAS -> Text(contexto.getString(R.string.text_tipo_gas))
    }
}

// Función que utilizo para convertir las fechas que vienen desde la BD en string
fun localDateToString(date: LocalDate, pattern: String = "yyyy-MM-dd"): String {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return date.format(formatter)
}