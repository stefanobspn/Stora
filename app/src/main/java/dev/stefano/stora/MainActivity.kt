package dev.stefano.stora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.stefano.stora.ui.navigation.AddProductRoute
import dev.stefano.stora.ui.product.AddProductView
import dev.stefano.stora.ui.navigation.DashBoardRoute
import dev.stefano.stora.ui.navigation.ProductRoute
import dev.stefano.stora.ui.navigation.TransactionRoute
import dev.stefano.stora.ui.navigation.ReportRoute
import dev.stefano.stora.ui.product.ProductView
import dev.stefano.stora.ui.dashboard.DashboardView
import dev.stefano.stora.ui.dashboard.DashboardViewModel
import dev.stefano.stora.ui.report.ReportView
import dev.stefano.stora.ui.report.ReportViewModel
import dev.stefano.stora.ui.transaction.TransactionView
import dev.stefano.stora.ui.transaction.TransactionViewModel
import dev.stefano.stora.ui.shared.ProductViewModel
import dev.stefano.stora.ui.theme.StoraTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StoraTheme{
                val navController = rememberNavController()
                val productViewModel: ProductViewModel = hiltViewModel()

                NavHost(
                    navController = navController,
                    startDestination = DashBoardRoute
                ) {
                    composable<DashBoardRoute> {
                        val dashboardViewModel: DashboardViewModel = hiltViewModel()
                        DashboardView(
                            viewModel = dashboardViewModel,
                            onNavigateToProduct = {
                                navController.navigate(ProductRoute)
                            },
                            onNavigateToTransaction = {
                                navController.navigate(TransactionRoute)
                            },
                            onNavigateToReport = {
                                navController.navigate(ReportRoute)
                            }
                        )
                    }

                    composable<ReportRoute> {
                        val reportViewModel: ReportViewModel = hiltViewModel()
                        ReportView(
                            viewModel = reportViewModel,
                            onNavigateBack = {
                                navController.popBackStack()
                            }
                        )
                    }

                    composable<TransactionRoute> {
                        val transactionViewModel: TransactionViewModel = hiltViewModel()
                        TransactionView(
                            viewModel = transactionViewModel,
                            onNavigateBack = {
                                navController.popBackStack()
                            }
                        )
                    }

                    composable<ProductRoute> {
                        ProductView(
                            viewModel = productViewModel,
                            onNavigateToAddProduct = {
                                navController.navigate(AddProductRoute)
                            },
                            onNavigateBack = {
                                navController.popBackStack()
                            }
                        )
                    }

                    composable<AddProductRoute> {
                        AddProductView(
                            viewModel = productViewModel,
                            onNavigateBack = {
                                navController.popBackStack()
                            }
                        )
                    }
                }

            }

        }
    }
}