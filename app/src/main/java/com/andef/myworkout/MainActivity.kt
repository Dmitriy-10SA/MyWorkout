package com.andef.myworkout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.andef.myworkout.bottombar.ui.UiBottomBar
import com.andef.myworkout.design.scaffold.ui.UiScaffold
import com.andef.myworkout.di.viewmodel.ViewModelFactory
import com.andef.myworkout.navigation.graph.AppNavGraph
import com.andef.myworkout.ui.theme.MyWorkoutTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (application as MyWorkoutApplication).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navHostController = rememberNavController()

            MyWorkoutTheme {
                UiScaffold(
                    bottomBar = { UiBottomBar(navHostController = navHostController) }
                ) { paddingValues ->
                    AppNavGraph(
                        paddingValues = paddingValues,
                        navHostController = navHostController,
                        viewModelFactory = viewModelFactory
                    )
                }
            }
        }
    }
}