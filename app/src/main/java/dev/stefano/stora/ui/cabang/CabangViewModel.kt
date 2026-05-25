package dev.stefano.stora.ui.cabang

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.stefano.stora.data.model.Cabang
import dev.stefano.stora.data.repository.CabangRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CabangViewModel @Inject constructor(
    private val repository: CabangRepository
) : ViewModel() {

    val allCabang: StateFlow<List<Cabang>> = repository.getAllCabang()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    var cabangName by mutableStateOf("")
        private set
    var cabangAddress by mutableStateOf("")
        private set
    var cabangPhone by mutableStateOf("")
        private set

    var editingCabang by mutableStateOf<Cabang?>(null)
        private set

    fun onNameChange(newName: String) { cabangName = newName }
    fun onAddressChange(newAddress: String) { cabangAddress = newAddress }
    fun onPhoneChange(newPhone: String) { cabangPhone = newPhone }

    fun setCabangForEditing(cabang: Cabang?) {
        editingCabang = cabang
        if (cabang != null) {
            cabangName = cabang.name
            cabangAddress = cabang.address
            cabangPhone = cabang.phoneNumber
        } else {
            clearForm()
        }
    }

    fun saveCabang() {
        if (cabangName.isNotBlank() && cabangAddress.isNotBlank()) {
            viewModelScope.launch {
                val currentCabang = editingCabang
                if (currentCabang == null) {
                    repository.insertCabang(Cabang(name = cabangName, address = cabangAddress, phoneNumber = cabangPhone))
                } else {
                    repository.updateCabang(currentCabang.copy(name = cabangName, address = cabangAddress, phoneNumber = cabangPhone))
                }
                clearForm()
            }
        }
    }

    fun deleteCabang() {
        editingCabang?.let { cabang ->
            viewModelScope.launch {
                repository.deleteCabang(cabang)
                clearForm()
            }
        }
    }

    fun clearForm() {
        cabangName = ""
        cabangAddress = ""
        cabangPhone = ""
        editingCabang = null
    }
}
