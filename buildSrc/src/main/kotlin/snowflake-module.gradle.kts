/*
 * 🐻‍❄️❄ Snowflake: Easy to use Kotlin library to help you generate Twitter snowflakes asynchronously
 * Copyright (c) 2022-2023 Noelware Team <team@noelware.org>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import org.noelware.charted.snowflake.gradle.*

plugins {
    kotlin("plugin.serialization")
    id("com.diffplug.spotless")
    id("org.jetbrains.dokka")
    kotlin("multiplatform")
    id("kotlinx-atomicfu")

    `maven-publish`
    `java-library`
}

group = "org.noelware.charted.snowflake"
version = "$VERSION"

atomicfu {
    dependenciesVersion = "0.18.5"
}

repositories {
    mavenCentral()
}

kotlin {
    explicitApi()
    jvm {
        withJava()
        compilations.all {
            kotlinOptions.jvmTarget = JAVA_VERSION.toString()
            kotlinOptions.javaParameters = true
            kotlinOptions.freeCompilerArgs += listOf("-opt-in=kotlin.RequiresOptIn")
        }
    }

    js {
        browser()
        nodejs()
    }

    val os = System.getProperty("os.name")
    val arch = System.getProperty("os.arch")

    when {
        os.startsWith("Windows") -> {
            mingwX64("native")
        }

        os == "Linux" -> {
            when (arch) {
                "amd64" -> linuxX64("native")
                "arm64", "aarch64" -> linuxArm64("native")
                else -> error("Linux with architecture $arch is not supported.")
            }
        }

        os == "Mac OS X" -> {
            when (arch) {
                "x86_64" -> macosX64("native")
                "arm64" -> macosArm64("native")
                else -> error("macOS with architecture $arch is not supported.")
            }
        }

        else -> error("Operating system $os is not supported.")
    }

    sourceSets {
        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }

        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.4.1")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
            }
        }

        val jvmMain by getting
        val jsMain by getting
        val nativeMain by getting
    }
}

spotless {
    kotlin {
        target("src/**/*.kt", "buildSrc/**/*.kt", "buildSrc/**/*.kts")
        licenseHeaderFile("${rootProject.projectDir}/assets/HEADING")
        trimTrailingWhitespace()
        endWithNewline()

        // We can't use the .editorconfig file, so we'll have to specify it here
        // issue: https://github.com/diffplug/spotless/issues/142
        ktlint()
            .setUseExperimental(true)
            .editorConfigOverride(mapOf(
                "indent_size" to "4",
                "ktlint_disabled_rules" to "colon-spacing,annotation-spacing,filename,no-wildcard-imports,argument-list-wrapping",
                "ij_kotlin_allow_trailing_comma" to "false",
                "ktlint_code_style" to "official",
                "no-unused-imports" to "true",
                "no-unit-return" to "true",
                "no-consecutive-blank-lines" to "true",
                "experimental:fun-keyword-spacing" to "true",
                "experimental:unnecessary-parentheses-before-trailing-lambda" to "true"
            ))
    }
}
