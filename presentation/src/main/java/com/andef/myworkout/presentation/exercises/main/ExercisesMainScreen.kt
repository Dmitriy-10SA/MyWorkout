package com.andef.myworkout.presentation.exercises.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.andef.myworkout.R
import com.andef.myworkout.ViewModelFactory
import com.andef.myworkout.design.fab.state.UiFABState
import com.andef.myworkout.design.fab.ui.UiFAB
import com.andef.myworkout.design.scaffold.ui.UiScaffold
import com.andef.myworkout.navigation.Screen

@Composable
fun ExercisesMainScreen(
    paddingValues: PaddingValues,
    viewModelFactory: ViewModelFactory,
    navHostController: NavHostController
) {
    UiScaffold(
        floatingActionButton = {
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
    ) { topPadding ->

    }
}