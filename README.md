# 🐻‍❄️❄️ Snowflake
> *Easy to use Kotlin library to help you generate Twitter snowflakes asynchronously*
> 
> <kbd>[v0.1-beta](https://github.com/charted-dev/snowflake/releases/v0.1-beta)</kbd>

**Snowflake** is a Kotlin multiplatform library to help you generate Twitter snowflakes in a single node environment or in a clustered environment.
This library was majorly inspired by [@bwmarrin](https://github.com/bwmarrin)'s [Go Snowflake](https://github.com/bwmarrin/snowflake) library.

## Features
- Distributed snowflake to help you generate snowflakes on each node with the `SnowflakeNodes` API.
- **kotlinx.serialization** support to easily (de)serialize snowflakes.
- Simple and usable way to generate Twitter snowflakes
- **Asynchronous** support via Kotlin's coroutines

## Usage
View the [installation guide](#installation) on how to install the library in your Kotlin project.

The formatting of the snowflake is relatively the same as the [ID Format](https://github.com/bwmarrin/snowflake#id-format) that
Twitter uses.

```kotlin
import org.noelware.charted.snowflake.extensions.*
import org.noelware.charted.snowflake.Snowflake

val snowflake = Snowflake {
    epoch = 1288834974657 /* custom epoch (in milliseconds) */
    node  = 0            /* node id */
}

val id = snowflake.generate()
// => returns [org.noelware.charted.snowflake.ID]

id.nodeId /* node id provided by the Snowflake builder */
id.epoch /* epoch (in milliseconds) */
id.timeSinceEpoch() /* kotlinx.datetime.LocalDateTime when the snowflake was created */
```

## Benchmarking
Since generating snowflakes should be as fast as humanly possible, we provide benchmarks in the [benchmarks](./benchmarks) folder with JMH. You
can run the benchmarks on your own hardware, but for simplicity, this has been run on Noel's main rig (AMD Ryzen 7 2700X @ 3.700GHZ, NVIDIA GTX 1070 TI).

```shell

```

## Installation
To install the **Snowflake** library, you will need to enable Noelware's [Maven repository](https://maven.noelware.org).

### Gradle (Kotlin DSL)
```kotlin
repositories {
  maven("https://maven.noelware.org")
  mavenCentral()
}

dependencies {
  implementation("org.noelware.charted.snowflake:snowflake:0.1-alpha")
}
```

### Gradle (Groovy DSL)
```groovy
repositories {
  maven "https://maven.noelware.org"
  mavenCentral()
}

dependencies {
  implementation "org.noelware.charted.snowflake:snowflake:0.1-alpha"
}
```

### Maven
```xml
<repositories>
  <repository>
    <url>https://maven.noelware.org</url>
  </repository>
</repositories>
<dependencies>
  <dependency>
    <groupId>org.noelware.charted.snowflake</groupId>
    <artifactId>snowflake-jvm</artifactId>
    <version>0.1-alpha</version>
    <type>pom</type>
  </dependency>
</dependencies>
```

## License
**snowflake** is released under the **MIT License** with love by Noelware. <3
