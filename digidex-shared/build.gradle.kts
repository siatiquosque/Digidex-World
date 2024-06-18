import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.ksp)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    kotlin("plugin.serialization")
//    alias(libs.plugins.androidx.room)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    jvm("desktop")

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "digidex-shared"
            isStatic = true
            // Required when using NativeSQLiteDriver
            linkerOpts.add("-lsqlite3")
        }
    }


    sourceSets {
        commonMain.dependencies {

            api(libs.kotlinx.coroutines.core)

            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.0")

            implementation(compose.components.resources)
            //Room
            api(libs.androidx.room)
            api(libs.androidx.sqlite.bundled)

            //Koin
            api(project.dependencies.platform(libs.koin.bom))
            api(libs.koin.core)
            api(libs.koin.compose)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.koin.test)
            implementation("io.mockative:mockative:2.2.2")
        }

        androidMain.dependencies {
            implementation(libs.androidx.room)
            api(libs.koin.android)

        }

        iosMain.dependencies {
            implementation("co.touchlab:sqliter-driver:1.3.1")
        }


    }
}
task("testClasses")

dependencies {
    configurations
        .filter { it.name.startsWith("ksp") && it.name.contains("Test") }
        .forEach {
            add(it.name, "io.mockative:mockative-processor:2.2.2")
        }

    ksp(libs.androidx.room.ksp)

}

android {
    namespace = "br.com.siatiquosque.digidex_shared"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

//room {
//    schemaDirectory("$projectDir/schemas")
//}
