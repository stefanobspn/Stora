package dev.stefano.stora.ui.cabang

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import dev.stefano.stora.ui.product.Field

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCabangView(
    viewModel: CabangViewModel,
    onNavigateBack: () -> Unit
) {
    val isEditing = viewModel.editingCabang != null

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(if (isEditing) "Edit Cabang" else "Tambah Cabang") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                },
                actions = {
                    if (isEditing) {
                        IconButton(onClick = {
                            viewModel.deleteCabang()
                            onNavigateBack()
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Hapus Cabang")
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Field(
                value = viewModel.cabangName,
                onValueChange = { viewModel.onNameChange(it) },
                label = { Text("Nama Cabang") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            Field(
                value = viewModel.cabangAddress,
                onValueChange = { viewModel.onAddressChange(it) },
                label = { Text("Alamat") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            Field(
                value = viewModel.cabangPhone,
                onValueChange = { viewModel.onPhoneChange(it) },
                label = { Text("Nomor Telepon") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )
            Button(
                onClick = {
                    viewModel.saveCabang()
                    onNavigateBack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (isEditing) "Simpan Perubahan" else "Simpan Cabang")
            }
        }
    }
}
