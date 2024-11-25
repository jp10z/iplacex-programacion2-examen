package cl.joseramos.android.iplacex_programacion2_examen.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.joseramos.android.iplacex_programacion2_examen.R
import cl.joseramos.android.iplacex_programacion2_examen.db.Medicion
import cl.joseramos.android.iplacex_programacion2_examen.db.TipoMedidor
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale

// constante para indicar cual será el padding de izquierda y derecha del formulario
val HORIZONAL_PADDING = 20.dp

@Preview(showSystemUi = true)
@Composable
fun IngresoUI(
    onClickIrAListado:() -> Unit = {},
    vmListaMediciones: ListaMedicionesViewModel = viewModel(factory = ListaMedicionesViewModel.Factory)
) {
    // almaceno el contexto, que me servirá para leer las traducciones
    val contexto = LocalContext.current

    // mutableStates para almacenar los valores del formulario
    var valorMedidor by remember { mutableStateOf("") }
    var fechaMedicion by remember { mutableStateOf(
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
            Date()
        )) }
    var tipoMedidor by remember { mutableStateOf(TipoMedidor.AGUA.toString()) }


    Scaffold(
        content = { padding ->
            Column (
                modifier = Modifier.padding(padding).fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    contexto.getString(R.string.text_registro_medidor),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    ),
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                // TextFied del valor del medidor
                TextField(
                    label = { Text(contexto.getString(R.string.text_medidor)) },
                    value = valorMedidor,
                    // Limitar carácteres a solo digitos numéricos
                    // lo que se hace es básicamente sobreescribir el mutableState si todos
                    // los carácteres son numéricos, sino ignora el seteo
                    onValueChange = {
                        if (it.all { char -> char.isDigit() }) {
                            valorMedidor = it
                        }
                    },
                    modifier = Modifier
                        .padding(horizontal = HORIZONAL_PADDING)
                        .height(56.dp)
                        .fillMaxWidth(),
                )
                // TextField de la fecha de medición
                TextField(
                    label = { Text(contexto.getString(R.string.text_fecha)) },
                    value = fechaMedicion,
                    onValueChange = { fechaMedicion = it },
                    modifier = Modifier
                        .padding(horizontal = HORIZONAL_PADDING)
                        .height(56.dp)
                        .fillMaxWidth()
                )
                // Creo una nueva columna para resetear el alineamiento y para organizar mejor
                // los RadioButtons
                Column(
                    modifier = Modifier
                        .padding(horizontal = HORIZONAL_PADDING)
                        .padding(top = 16.dp)
                        .fillMaxWidth()
                        .align(Alignment.Start)
                ) {
                    Text(text = "${contexto.getString(R.string.text_medidor_de)}:")
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = tipoMedidor == TipoMedidor.AGUA.toString(),
                            onClick = { tipoMedidor = TipoMedidor.AGUA.toString() }
                        )
                        Text(text = contexto.getString(R.string.text_tipo_agua))
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = tipoMedidor == TipoMedidor.LUZ.toString(),
                            onClick = { tipoMedidor = TipoMedidor.LUZ.toString() }
                        )
                        Text(text = contexto.getString(R.string.text_tipo_luz))
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = tipoMedidor == TipoMedidor.GAS.toString(),
                            onClick = { tipoMedidor = TipoMedidor.GAS.toString() }
                        )
                        Text(text = contexto.getString(R.string.text_tipo_gas))
                    }
                }

                // Botón de guardado
                Button(
                    onClick = {
                        // validaciones, en caso de que ocurra un error se gatilla un toast
                        // y no se ejecuta el ingreso en la Base de datos
                        var validationOk: Boolean = true
                        // validar la fecha mediante una conversión
                        try {
                            LocalDate.parse(fechaMedicion).toString()
                        }catch(e: Exception) {
                            validationOk = false
                        }
                        // validar si el valor es vacío, no es necesario validar si es número
                        // ya que fuerzo que solo se ingresen numeros mediante el onValueChange
                        // del widget
                        if (valorMedidor == "") validationOk = false
                        if (validationOk) {
                            // se inserta la medición mediante el viewModel, este tiene la corutina
                            vmListaMediciones.insertarMedicion(
                                Medicion(
                                    valor = valorMedidor.toIntOrNull() ?: 0,
                                    fecha = LocalDate.parse(fechaMedicion),
                                    tipo = tipoMedidor
                                )
                            )
                            // se muestra toast con la confirmación
                            Toast.makeText(contexto, contexto.getString(R.string.text_toast_medicion_agregada), Toast.LENGTH_SHORT).show()
                            // se redirige a la pantalla de listado
                            onClickIrAListado()
                        }else{
                            // gatillar toast en caso de error
                            Toast.makeText(contexto, contexto.getString(R.string.text_toast_error_al_guardar), Toast.LENGTH_SHORT).show()
                        }
                    }
                ) {
                    Text(contexto.getString(R.string.text_btn_registrar_medicion))
                }
            }
        }
    )
}