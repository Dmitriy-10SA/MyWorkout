package com.andef.myworkout.presentation.exercises.main

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.andef.myworkout.R
import com.andef.myworkout.design.fab.state.UiFABState
import com.andef.myworkout.design.fab.ui.UiFAB
import com.andef.myworkout.design.scaffold.ui.UiScaffold
import com.andef.myworkout.di.viewmodel.ViewModelFactory
import com.andef.myworkout.navigation.Screen
import com.andef.myworkout.ui.utils.slideInUp
import com.andef.myworkout.ui.utils.slideOutDown

@Composable
fun ExercisesScreen(
    paddingValues: PaddingValues,
    viewModelFactory: ViewModelFactory,
    navHostController: NavHostController
) {
    val viewModel: ExercisesScreenViewModel = viewModel(factory = viewModelFactory)
    val state = viewModel.state.collectAsState()

    UiScaffold(
        floatingActionButton = {
            AnimatedContent(
                targetState = !state.value.isError,
                transitionSpec = { slideInUp.togetherWith(slideOutDown) }
            ) { state ->
                if (state) {
                    FABContent(paddingValues = paddingValues, navHostController = navHostController)
                }
            }
        }
    ) { topPadding ->

    }
}

@Composable
private fun FABContent(paddingValues: PaddingValues, navHostController: NavHostController) {
    UiFAB(
        modifier = Modifier
            .padding(bottom = paddingValues.calculateBottomPadding()),
        painter = painterResource(R.drawable.add),
        contentDescription = stringResource(R.string.add_exercise),
        state = UiFABState.Base
    ) {
        navHostController.navigate(
            Screen.ExercisesScreen.AddOrChangeScreen.passParams(isAdd = true)
        )
    }
}