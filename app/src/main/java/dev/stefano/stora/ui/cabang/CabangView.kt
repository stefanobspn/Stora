package dev.stefano.stora.ui.cabang

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Shop
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.stefano.stora.data.model.Cabang

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CabangView(
    viewModel: CabangViewModel,
    onNavigateToAddCabang: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val cabangList by viewModel.allCabang.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Cabang") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.setCabangForEditing(null)
                onNavigateToAddCabang()
            }) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Cabang")
            }
        }
    ) { innerPadding ->
        if (cabangList.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Belum ada cabang.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(cabangList) { cabang ->
                    CabangItemRow(cabang = cabang, onClick = {
                        viewModel.setCabangForEditing(cabang)
                        onNavigateToAddCabang()
                    })
                }
            }
        }
    }
}

@Composable
fun CabangItemRow(cabang: Cabang, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Shop, contentDescription = null, modifier = Modifier.size(40.dp))
            Spacer(Modifier.width(16.dp))
            Column {
                Text(cabang.name, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                Text(cabang.address, style = MaterialTheme.typography.bodyMedium)
                Text(cabang.phoneNumber, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
