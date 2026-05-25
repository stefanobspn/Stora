package dev.stefano.stora.ui.pegawai

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.stefano.stora.data.model.Pegawai
import dev.stefano.stora.data.repository.PegawaiRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PegawaiViewModel @Inject constructor(
    private val repository: PegawaiRepository
) : ViewModel() {

    val allPegawai: StateFlow<List<Pegawai>> = repository.getAllPegawai()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    var pegawaiName by mutableStateOf("")
        private set
    var pegawaiPosition by mutableStateOf("")
        private set
    var pegawaiPhone by mutableStateOf("")
        private set

    var editingPegawai by mutableStateOf<Pegawai?>(null)
        private set

    fun onNameChange(newName: String) { pegawaiName = newName }
    fun onPositionChange(newPosition: String) { pegawaiPosition = newPosition }
    fun onPhoneChange(newPhone: String) { pegawaiPhone = newPhone }

    fun setPegawaiForEditing(pegawai: Pegawai?) {
        editingPegawai = pegawai
        if (pegawai != null) {
            pegawaiName = pegawai.name
            pegawaiPosition = pegawai.position
            pegawaiPhone = pegawai.phoneNumber
        } else {
            clearForm()
        }
    }

    fun savePegawai() {
        if (pegawaiName.isNotBlank() && pegawaiPosition.isNotBlank()) {
            viewModelScope.launch {
                val currentPegawai = editingPegawai
                if (currentPegawai == null) {
                    repository.insertPegawai(Pegawai(name = pegawaiName, position = pegawaiPosition, phoneNumber = pegawaiPhone))
                } else {
                    repository.updatePegawai(currentPegawai.copy(name = pegawaiName, position = pegawaiPosition, phoneNumber = pegawaiPhone))
                }
                clearForm()
            }
        }
    }

    fun deletePegawai() {
        editingPegawai?.let { pegawai ->
            viewModelScope.launch {
                repository.deletePegawai(pegawai)
                clearForm()
            }
        }
    }

    fun clearForm() {
        pegawaiName = ""
        pegawaiPosition = ""
        pegawaiPhone = ""
        editingPegawai = null
    }
}
