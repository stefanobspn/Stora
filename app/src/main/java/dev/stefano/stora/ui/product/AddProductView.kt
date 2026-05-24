package dev.stefano.stora.ui.product

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
import dev.stefano.stora.ui.shared.ProductViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductView(
    viewModel: ProductViewModel,
    onNavigateBack: () -> Unit
) {
    val isEditing = viewModel.editingProduct != null
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(if (isEditing) "Edit Produk" else "Tambah Produk") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                },
                actions = {
                    if (isEditing) {
                        IconButton(onClick = {
                            viewModel.deleteProduct()
                            onNavigateBack()
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Hapus Produk")
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
                value = viewModel.productName,
                onValueChange = { viewModel.onNameChange(it) },
                label = { Text("Nama") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            Field(
                value = viewModel.productPrice,
                onValueChange = { viewModel.onPriceChange(it) },
                label = { Text("Harga") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Field(
                value = viewModel.productStock,
                onValueChange = { viewModel.onStockChange(it) },
                label = { Text("Stok") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Button(
                onClick = {
                    viewModel.saveProduct()
                    onNavigateBack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (isEditing) "Simpan Perubahan" else "Simpan Produk")
            }
        }
    }
}

@Composable
fun Field(
    value: String,
    onValueChange: (String) -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = label,
        modifier = modifier.fillMaxWidth(),
        keyboardOptions = keyboardOptions,
        maxLines = 1
    )
}