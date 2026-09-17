import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    `maven-publish`
}

android {
    namespace = "com.ferhatozcelik.wear.example.common"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdkWear.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

dependencies {
    // Shared model + Wearable Data Layer helpers
    api(libs.play.services.wearable)
    api(libs.gson)

    implementation(libs.androidx.core.ktx)
}

publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = "com.ferhatozcelik"
            artifactId = "wear-common"
            version = providers.gradleProperty("VERSION_NAME").getOrElse("1.0.0")

            afterEvaluate { from(components["release"]) }

            pom {
                name.set("wear-common")
                description.set("Shared data model and Wearable Data Layer helpers for the Wear App Example.")
                url.set("https://github.com/ferhatozcelik/wear-os-example")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("ferhatozcelik")
                        name.set("Ferhat Ozcelik")
                        url.set("https://github.com/ferhatozcelik")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/ferhatozcelik/wear-os-example.git")
                    developerConnection.set("scm:git:ssh://github.com/ferhatozcelik/wear-os-example.git")
                    url.set("https://github.com/ferhatozcelik/wear-os-example")
                }
            }
        }
    }
    repositories {
        mavenLocal()
    }
}
