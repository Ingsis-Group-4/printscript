plugins {
    id("printscript.gradle.kotlin-common-conventions")
}

dependencies {
    implementation(project(":common"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.0")
}