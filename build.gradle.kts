// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version ("7.1.0") apply (false)
    id("com.android.library") version ("7.1.0") apply (false)
    id("org.jetbrains.kotlin.android") version ("1.6.10") apply (false)
    id ("androidx.navigation.safeargs.kotlin") version ("2.5.0-alpha02") apply(false)
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}