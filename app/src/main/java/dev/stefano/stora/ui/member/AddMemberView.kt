package dev.stefano.stora.ui.member

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
fun AddMemberView(
    viewModel: MemberViewModel,
    onNavigateBack: () -> Unit
) {
    val isEditing = viewModel.editingMember != null

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(if (isEditing) "Edit Member" else "Tambah Member") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                },
                actions = {
                    if (isEditing) {
                        IconButton(onClick = {
                            viewModel.deleteMember()
                            onNavigateBack()
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Hapus Member")
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
                value = viewModel.memberName,
                onValueChange = { viewModel.onNameChange(it) },
                label = { Text("Nama") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            Field(
                value = viewModel.memberPhone,
                onValueChange = { viewModel.onPhoneChange(it) },
                label = { Text("Nomor Telepon") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )
            Field(
                value = viewModel.memberPoints,
                onValueChange = { viewModel.onPointsChange(it) },
                label = { Text("Poin") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Button(
                onClick = {
                    viewModel.saveMember()
                    onNavigateBack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (isEditing) "Simpan Perubahan" else "Simpan Member")
            }
        }
    }
}
