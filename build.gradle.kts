// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id("com.google.devtools.ksp") version "2.0.21-1.0.26" apply false
}
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.gradle)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.21")
    }
}

tasks.register("clean", Delete::class.java) {
    delete(rootProject.layout.buildDirectory)
}