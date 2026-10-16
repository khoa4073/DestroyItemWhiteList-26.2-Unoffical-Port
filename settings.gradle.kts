pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/")
    }
}

plugins {
    id("dev.kikugie.stonecutter") version "0.6"
    id("dev.isxander.modstitch.base") version "0.8.5" apply false
    //id("dev.kikugie.j52j") version "1.0.2" apply false // Enables asset processing by writing json5 files
    //id("me.modmuss50.mod-publish-plugin") version "0.7.+" apply false // Publishes mods to hosting websites
}

stonecutter {
    kotlinController = true
    centralScript = "build.gradle.kts"

    shared {
        versions("1.21.1", "1.21.4", "1.21.5", "1.21.8", "1.21.10", "26.2")
    }
    create(rootProject)
}

rootProject.name = "DestroyItemWhiteList"