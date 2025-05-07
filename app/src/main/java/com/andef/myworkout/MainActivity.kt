package com.andef.myworkout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.andef.myworkout.bottombar.ui.UiBottomBar
import com.andef.myworkout.navigation.graph.AppNavGraph
import com.andef.myworkout.ui.theme.Black
import com.andef.myworkout.ui.theme.MyWorkoutTheme
import com.andef.myworkout.ui.theme.White

class MainActivity : ComponentActivity() {
//    @Inject
//    lateinit var viewModelFactory: ViewModelFactory

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
                Content(navHostController = navHostController)
            }
        }
    }
}

@Composable
private fun Content(navHostController: NavHostController) {
    Scaffold(
        containerColor = White,
        contentColor = Black,
        bottomBar = { UiBottomBar(navHostController = navHostController) }
    ) { paddingValues ->
        AppNavGraph(
            paddingValues = paddingValues,
            navHostController = navHostController
        )
    }
}