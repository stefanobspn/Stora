package dev.stefano.stora.ui.transaction

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.stefano.stora.data.model.Product
import dev.stefano.stora.data.model.TransactionEntity
import dev.stefano.stora.data.model.TransactionItemEntity
import dev.stefano.stora.data.repository.ProductRepository
import dev.stefano.stora.data.repository.TransactionRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CartItem(
    val product: Product,
    val quantity: Int
)

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    val allProducts: StateFlow<List<Product>> = productRepository.getAllProducts()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _cartItems = mutableStateListOf<CartItem>()
    val cartItems: List<CartItem> get() = _cartItems

    var totalAmount by mutableStateOf(0.0)
        private set

    fun addToCart(product: Product) {
        val index = _cartItems.indexOfFirst { it.product.id == product.id }
        if (index != -1) {
            val existingItem = _cartItems[index]
            _cartItems[index] = existingItem.copy(quantity = existingItem.quantity + 1)
        } else {
            _cartItems.add(CartItem(product, 1))
        }
        calculateTotal()
    }

    fun removeFromCart(product: Product) {
        val index = _cartItems.indexOfFirst { it.product.id == product.id }
        if (index != -1) {
            val existingItem = _cartItems[index]
            if (existingItem.quantity > 1) {
                _cartItems[index] = existingItem.copy(quantity = existingItem.quantity - 1)
            } else {
                _cartItems.removeAt(index)
            }
        }
        calculateTotal()
    }

    private fun calculateTotal() {
        totalAmount = _cartItems.sumOf { it.product.price * it.quantity }
    }

    fun checkout(onSuccess: () -> Unit) {
        if (_cartItems.isEmpty()) return

        viewModelScope.launch {
            val transaction = TransactionEntity(totalAmount = totalAmount)
            val items = _cartItems.map {
                TransactionItemEntity(
                    transactionId = 0, // Will be set in repository
                    productId = it.product.id,
                    productName = it.product.name,
                    quantity = it.quantity,
                    price = it.product.price
                )
            }
            transactionRepository.saveTransaction(transaction, items)
            _cartItems.clear()
            totalAmount = 0.0
            onSuccess()
        }
    }
}
