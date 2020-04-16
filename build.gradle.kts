plugins {
    kotlin("jvm") version "1.3.61"
    application
    id("org.jlleitschuh.gradle.ktlint") version "9.2.1"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.github.doyaaaaaken:kotlin-csv-jvm:0.7.3")

    testImplementation("io.kotest:kotest-runner-junit5-jvm:4.0.2") // for kotest framework
    testImplementation("io.kotest:kotest-assertions-core-jvm:4.0.2") // for kotest core jvm assertions
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

application {
    mainClassName = "MainKt"
}

tasks.withType<Test> {
    useJUnitPlatform()
}