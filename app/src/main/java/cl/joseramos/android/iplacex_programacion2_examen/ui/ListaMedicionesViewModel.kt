package cl.joseramos.android.iplacex_programacion2_examen.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import cl.joseramos.android.iplacex_programacion2_examen.Aplicacion
import cl.joseramos.android.iplacex_programacion2_examen.db.Medicion
import cl.joseramos.android.iplacex_programacion2_examen.db.MedicionDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListaMedicionesViewModel(val medicionDao:MedicionDao): ViewModel() {

    var mediciones by mutableStateOf(listOf<Medicion>())

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = createSavedStateHandle()
                val aplicacion = (this[APPLICATION_KEY] as Aplicacion)
                ListaMedicionesViewModel(aplicacion.medicionDao)
            }
        }
    }

    fun obtenerMediciones(): List<Medicion> {
        viewModelScope.launch(Dispatchers.IO) {
            mediciones = medicionDao.getAll()
        }
        return mediciones
    }

    // pendiente (quizás no sea necesario)
    fun insertarMedicion(medicion: Medicion){
        viewModelScope.launch(Dispatchers.IO) {
            medicionDao.insertAll(medicion)
            obtenerMediciones()
        }
    }
}