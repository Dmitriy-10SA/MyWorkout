package com.andef.myworkout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.andef.myworkout.bottombar.ui.UiBottomBar
import com.andef.myworkout.design.snackbar.state.UiSnackBarState
import com.andef.myworkout.design.snackbar.ui.UiSnackBar
import com.andef.myworkout.di.viewmodel.ViewModelFactory
import com.andef.myworkout.navigation.graph.AppNavGraph
import com.andef.myworkout.ui.theme.Black
import com.andef.myworkout.ui.theme.MyWorkoutTheme
import com.andef.myworkout.ui.theme.White
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
            val snackBarHostState = remember { SnackbarHostState() }

            MyWorkoutTheme {
                Content(
                    viewModelFactory = viewModelFactory,
                    navHostController = navHostController,
                    snackBarHostState = snackBarHostState
                )
            }
        }
    }
}

@Composable
private fun Content(
    viewModelFactory: ViewModelFactory,
    navHostController: NavHostController,
    snackBarHostState: SnackbarHostState
) {
    Scaffold(
        containerColor = White,
        contentColor = Black,
        bottomBar = {
            UiBottomBar(navHostController = navHostController)
        },
        snackbarHost = {
            UiSnackBar(
                state = UiSnackBarState.Error,
                snackBarHostState = snackBarHostState
            )
        }
    ) { paddingValues ->
        AppNavGraph(
            paddingValues = paddingValues,
            navHostController = navHostController,
            viewModelFactory = viewModelFactory,
            snackBarHostState = snackBarHostState
        )
    }
}