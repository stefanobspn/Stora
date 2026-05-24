package dev.stefano.stora.ui.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.stefano.stora.data.model.Product
import dev.stefano.stora.ui.shared.ProductViewModel
import dev.stefano.stora.ui.theme.StoraTheme
import java.text.NumberFormat
import java.util.Locale

@Composable
fun ProductView(
    viewModel: ProductViewModel,
    onNavigateToAddProduct: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val products by viewModel.allProducts.collectAsState()
    ProductViewContent(
        products = products,
        onNavigateToAddProduct = {
            viewModel.setProductForEditing(null)
            onNavigateToAddProduct()
        },
        onNavigateToEditProduct = { product ->
            viewModel.setProductForEditing(product)
            onNavigateToAddProduct()
        },
        onNavigateBack = onNavigateBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductViewContent(
    products: List<Product>,
    onNavigateToAddProduct: () -> Unit,
    onNavigateToEditProduct: (Product) -> Unit,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Produk") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAddProduct
            ) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Produk")
            }
        }
    ) {innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            if (products.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Belum ada produk.", style = MaterialTheme.typography.bodyLarge)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(products) {product ->
                        ProductItemRow(
                            product = product,
                            onClick = { onNavigateToEditProduct(product) }
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun ProductItemRow(
    product: Product,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(product.name, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                Text("Stok : ${product.stock}", style = MaterialTheme.typography.bodyMedium)
            }

            Text(
                product.price.toRupiahFormat(),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

private fun Double.toRupiahFormat(): String {
    val localeID = Locale("id", "ID")
    val numberFormat = NumberFormat.getNumberInstance(localeID)
    return "Rp ${numberFormat.format(this)}"
}

@Composable
@Preview(name = "Ada Produk")
fun ProductViewPreview() {
    val dummyProducts = listOf(
        Product(id = 1, name = "Kopi Hitam", price = 5000.0, stock = 10),
        Product(id = 2, name = "Indomie Goreng", price = 7000.0, stock = 25),
        Product(id = 3, name = "Es Teh Manis", price = 3000.0, stock = 50)
    )

    StoraTheme {
        ProductViewContent(
            products = dummyProducts,
            onNavigateToAddProduct = {},
            onNavigateToEditProduct = {},
            onNavigateBack = {}
        )
    }
}

@Composable
@Preview(name = "Gak ada Produk")
fun ProductViewEmptyPreview() {
    val dummyProducts = emptyList<Product>()

    StoraTheme {
        ProductViewContent(
            products = dummyProducts,
            onNavigateToAddProduct = {},
            onNavigateToEditProduct = {},
            onNavigateBack = {}
        )
    }
}