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

package org.noelware.charted.snowflake.gradle

import dev.floofy.utils.gradle.*
import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.TaskProvider
import org.gradle.api.tasks.bundling.Jar

fun MavenPublication.createPublicationMetadata(
    project: Project,
    sourcesJar: TaskProvider<Jar>,
    javadocJar: TaskProvider<Jar>
) {
    from(project.components.getByName("kotlin"))

    groupId = "org.noelware.charted.snowflake"
    artifactId = "snowflake"
    version = "$VERSION"

    artifact(sourcesJar.get())
    artifact(javadocJar.get())
    pom {
        description by "\uD83D\uDC3B\u200D❄️❄ Easy to use Kotlin library to help you generate Twitter snowflakes asynchronously"
        name by "snowflake"
        url by "https://charted-dev.github.io/snowflake"

        organization {
            name by "Noelware"
            url by "https://noelware.org"
        }

        developers {
            developer {
                name by "Noel"
                email by "cutie@floofy.dev"
                url by "https://floofy.dev"
            }

            developer {
                name by "Noelware Team"
                email by "team@noelware.org"
                url by "https://noelware.org"
            }
        }

        issueManagement {
            system by "GitHub"
            url by "https://github.com/charted-dev/snowflake/issues"
        }

        licenses {
            license {
                name by "MIT"
                url by "https://github.com/charted-dev/snowflake/blob/master/LICENSE"
            }
        }

        scm {
            connection by "scm:git:ssh://github.com/charted-dev/snowflake.git"
            developerConnection by "scm:git:ssh://git@github.com:charted-dev/snowflake.git"
            url by "https://github.com/charted-dev/snowflake"
        }
    }
}
