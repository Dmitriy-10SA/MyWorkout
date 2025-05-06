package com.andef.myworkout

import android.app.Application
import com.andef.myworkout.di.DaggerMyWorkoutComponent

class MyWorkoutApplication : Application() {
    val component by lazy {
        DaggerMyWorkoutComponent.factory().create(this)
    }
}