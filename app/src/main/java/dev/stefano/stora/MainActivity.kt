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
import dev.stefano.stora.ui.product.ProductView
import dev.stefano.stora.ui.dashboard.DashboardView
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
                        DashboardView(
                            onNavigateToProduct = {
                                navController.navigate(ProductRoute)
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