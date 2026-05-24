package dev.stefano.stora.ui.report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.stefano.stora.data.model.TransactionWithItems
import dev.stefano.stora.data.repository.TransactionRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    val allTransactions: StateFlow<List<TransactionWithItems>> = transactionRepository.getAllTransactions()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}
