package dev.stefano.stora.ui.shared

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.stefano.stora.data.model.Product
import dev.stefano.stora.data.repository.ProductRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductRepository
): ViewModel() {
    val allProducts: StateFlow<List<Product>> = repository.getAllProducts()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    var productName by mutableStateOf("")
        private set
    var productPrice by mutableStateOf("")
        private set
    var productStock by mutableStateOf("")
        private set

    var editingProduct by mutableStateOf<Product?>(null)
        private set

    fun onNameChange(newName: String) { productName = newName }
    fun onPriceChange(newPrice: String) { productPrice = newPrice }
    fun onStockChange(newStock: String) { productStock = newStock }

    fun setProductForEditing(product: Product?) {
        editingProduct = product
        if (product != null) {
            productName = product.name
            productPrice = product.price.toString()
            productStock = product.stock.toString()
        } else {
            clearForm()
        }
    }

    fun saveProduct() {
        val price = productPrice.toDoubleOrNull() ?: 0.0
        val stock = productStock.toIntOrNull() ?: 0
        if (productName.isNotBlank()) {
            viewModelScope.launch {
                val currentProduct = editingProduct
                if (currentProduct == null) {
                    repository.insertProduct(Product(name = productName, price = price, stock = stock))
                } else {
                    repository.updateProduct(currentProduct.copy(name = productName, price = price, stock = stock))
                }
                clearForm()
            }
        }
    }

    fun deleteProduct() {
        editingProduct?.let { product ->
            viewModelScope.launch {
                repository.deleteProduct(product)
                clearForm()
            }
        }
    }

    fun clearForm() {
        productName = ""
        productPrice = ""
        productStock = ""
        editingProduct = null
    }
}