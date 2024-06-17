plugins {
    kotlin("jvm") version "1.9.23"
}

group = "xyz.jeelzzz"
version = "0.1.0"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/releases/")
    maven("https://repo.papermc.io/repository/maven-public/")

}

dependencies {
    compileOnly("org.bukkit:bukkit:1.9-R0.1-SNAPSHOT")
    testImplementation(kotlin("test"))
    implementation(kotlin("reflect"))
}

configurations {
    configurations.testImplementation.get().apply {
        extendsFrom(configurations.compileOnly.get())
    }
}


tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}