package dev.stefano.stora.ui.pegawai

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.stefano.stora.data.model.Pegawai

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PegawaiView(
    viewModel: PegawaiViewModel,
    onNavigateToAddPegawai: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val allPegawai by viewModel.allPegawai.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Daftar Pegawai") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.setPegawaiForEditing(null)
                onNavigateToAddPegawai()
            }) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Pegawai")
            }
        }
    ) { innerPadding ->
        if (allPegawai.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Belum ada pegawai.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(allPegawai) { pegawai ->
                    PegawaiItemRow(pegawai = pegawai, onClick = {
                        viewModel.setPegawaiForEditing(pegawai)
                        onNavigateToAddPegawai()
                    })
                }
            }
        }
    }
}

@Composable
fun PegawaiItemRow(pegawai: Pegawai, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(40.dp))
            Spacer(Modifier.width(16.dp))
            Column {
                Text(pegawai.name, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                Text(pegawai.position, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)
                Text(pegawai.phoneNumber, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
