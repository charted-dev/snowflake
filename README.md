# 🐻‍❄️❄️ Snowflake
> *Easy to use Kotlin library to help you generate Twitter snowflakes asynchronously*
> 
> <kbd>[v0.1-beta](https://github.com/charted-dev/snowflake/releases/v0.1-beta)</kbd> | [:scroll: **Documentation**](https://charted-dev.github.io/snowflake)

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
// Defaults with node 0 and epoch of Twitter's snowflake
val snowflake = Snowflake()

val id = snowflake.generate()
// => returns [org.noelware.charted.snowflake.ID]
```

## Benchmaking
Since generating snowflakes should be fast as possible, we provide a benchmark suite in the [benchmarks](./benchmarks) folder with [kotlinx.benchmark](https://github.com/Kotlin/kotlinx-benchmark)

### Native
```
> Task :benchmarks:nativeBenchmark
Running 'main' benchmarks for 'native'
native: org.noelware.charted.snowflake.benchmarks.native.SnowflakeNativeBenchmarks.generateIds
Warm-up #0: 0.00240547 ms/op
Warm-up #1: 0.00380899 ms/op
Warm-up #2: 0.00514744 ms/op
Iteration #0: 0.00553965 ms/op
Iteration #1: 0.00633540 ms/op
Iteration #2: 0.00641139 ms/op
Iteration #3: 0.00698136 ms/op
Iteration #4: 0.00878726 ms/op
  ~ 0.00681101 ms/op ±15%

native summary:
Benchmark                              Mode  Cnt  Score   Error  Units
SnowflakeNativeBenchmarks.generateIds  avgt    5  0.007 ± 0.001  ms/op
```

### JVM
```
> Task :benchmarks:jvmBenchmark
Running 'main' benchmarks for 'jvm'
jvm: org.noelware.charted.snowflake.benchmarks.jvm.SnowflakeJvmBenchmarks.generateIds

Warm-up 1: 0.001 ms/op
Warm-up 2: ≈ 10⁻⁴ ms/op
Warm-up 3: ≈ 10⁻⁴ ms/op
Warm-up 4: ≈ 10⁻⁴ ms/op
Warm-up 5: ≈ 10⁻⁴ ms/op
Iteration 1: ≈ 10⁻⁴ ms/op
Iteration 2: ≈ 10⁻⁴ ms/op
Iteration 3: ≈ 10⁻⁴ ms/op
Iteration 4: ≈ 10⁻⁴ ms/op
Iteration 5: ≈ 10⁻⁴ ms/op

Warm-up 1: 0.001 ms/op
Warm-up 2: ≈ 10⁻⁴ ms/op
Warm-up 3: ≈ 10⁻⁴ ms/op
Warm-up 4: ≈ 10⁻⁴ ms/op
Warm-up 5: ≈ 10⁻⁴ ms/op
Iteration 1: ≈ 10⁻⁴ ms/op
Iteration 2: ≈ 10⁻⁴ ms/op
Iteration 3: ≈ 10⁻⁴ ms/op
Iteration 4: ≈ 10⁻⁴ ms/op
Iteration 5: ≈ 10⁻⁴ ms/op

Warm-up 1: 0.001 ms/op
Warm-up 2: ≈ 10⁻⁴ ms/op
Warm-up 3: ≈ 10⁻⁴ ms/op
Warm-up 4: ≈ 10⁻⁴ ms/op
Warm-up 5: ≈ 10⁻⁴ ms/op
Iteration 1: ≈ 10⁻⁴ ms/op
Iteration 2: ≈ 10⁻⁴ ms/op
Iteration 3: ≈ 10⁻⁴ ms/op
Iteration 4: ≈ 10⁻⁴ ms/op
Iteration 5: ≈ 10⁻⁴ ms/op

≈ 10⁻⁴ ms/op

jvm summary:
Benchmark                           Mode  Cnt   Score    Error  Units
SnowflakeJvmBenchmarks.generateIds  avgt   15  ≈ 10⁻⁴           ms/op
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
    implementation("org.noelware.charted.snowflake:snowflake:0.1-beta")
}
```

### Gradle (Groovy DSL)
```groovy
repositories {
    maven "https://maven.noelware.org"
    mavenCentral()
}

dependencies {
    implementation "org.noelware.charted.snowflake:snowflake:0.1-beta"
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
        <version>0.1-beta</version>
        <type>pom</type>
    </dependency>
</dependencies>
```

## License
**snowflake** is released under the **MIT License** with love by Noelware. <3
