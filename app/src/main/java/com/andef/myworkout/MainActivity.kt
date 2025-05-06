package com.andef.myworkout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import com.andef.myworkout.ui.theme.MyWorkoutTheme
import javax.inject.Inject

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
            MyWorkoutTheme {

            }
        }
    }
}