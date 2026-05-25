package dev.stefano.stora.ui.product

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import dev.stefano.stora.ui.shared.ProductViewModel
import dev.stefano.stora.utils.CurrencyUtils
import java.text.NumberFormat
import java.util.Locale


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
                onValueChange = { 
                    val cleanValue = CurrencyUtils.parseCurrency(it)
                    viewModel.onPriceChange(cleanValue) 
                },
                label = { Text("Harga") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                visualTransformation = CurrencyAmountInputVisualTransformation()
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
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = label,
        modifier = modifier.fillMaxWidth(),
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        maxLines = 1
    )
}

class CurrencyAmountInputVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val cleanText = text.text.replace(Regex("[^\\d]"), "")
        if (cleanText.isEmpty()) {
            return TransformedText(AnnotatedString(""), OffsetMapping.Identity)
        }

        val parsed = cleanText.toDoubleOrNull() ?: 0.0
        val formatter = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        formatter.maximumFractionDigits = 0
        val formatted = formatter.format(parsed).replace("Rp", "Rp ").trim()

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                val digitsBeforeOffset = text.text.take(offset).count { it.isDigit() }
                var digitsFound = 0
                for (i in formatted.indices) {
                    if (formatted[i].isDigit()) {
                        digitsFound++
                    }
                    if (digitsFound == digitsBeforeOffset && (i + 1 < formatted.length && !formatted[i + 1].isDigit() || i + 1 == formatted.length)) {
                        return i + 1
                    }
                }
                
                if (digitsBeforeOffset == 0) {
                   val firstDigitIndex = formatted.indexOfFirst { it.isDigit() }
                   return if (firstDigitIndex != -1) firstDigitIndex else 0
                }

                return formatted.length
            }

            override fun transformedToOriginal(offset: Int): Int {
                val digitsBeforeOffset = formatted.take(offset).count { it.isDigit() }
                var digitsFound = 0
                for (i in text.text.indices) {
                    if (text.text[i].isDigit()) {
                        digitsFound++
                    }
                    if (digitsFound == digitsBeforeOffset) {
                        return i + 1
                    }
                }
                return text.text.length
            }
        }

        return TransformedText(AnnotatedString(formatted), offsetMapping)
    }
}