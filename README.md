# firefly

[//]: # (![Test Status]&#40;https://github.com/mpschorr/fi/actions/workflows/WORKFLOW-FILE/badge.svg&#41;)
![Release Version](https://img.shields.io/github/v/release/mpschorr/firefly)
![License](https://img.shields.io/github/license/mpschorr/firefly)

**firefly** is a simple library for loading Bukkit configurations into Kotlin data classes

## Roadmap

- [x] Simple decoding with nested data classes
- [x] Custom decoders
- [x] Lists
- [ ] Maps
- [ ] Decorators for remapping

## Usage

Usage documentation can be found on the [wiki](https://github.com/mpschorr/firefly/wiki)

**Gradle**

```kotlin
repositories {
    maven("https://jitpack.io")
}

dependencies {
    implementation("com.github.mpschorr:firefly:0.1.0")
}
```

**Maven**
```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>

<dependency>
    <groupId>com.github.mpschorr</groupId>
    <artifactId>firefly</artifactId>
    <version>0.1.0</version>
</dependency>
```