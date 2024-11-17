package com.wuujcik.microbudget

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.generated.NavGraphs
import com.ramcosta.composedestinations.rememberNavHostEngine
import com.wuujcik.microbudget.ui.theme.MicroBudgetTheme
import org.koin.androidx.compose.KoinAndroidContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MicroBudgetTheme {
                KoinAndroidContext {
                    DestinationsNavHost(
                        navController = rememberNavController(),
                        navGraph = NavGraphs.root,
                        engine = rememberNavHostEngine(),
                    )
                }
            }
        }
    }
}
