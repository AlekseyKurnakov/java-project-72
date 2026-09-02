plugins {
    id("java")
    application
    id("com.gradleup.shadow") version "8.3.0"
}

application {
    mainClass.set("hexlet.code.App")
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation("io.javalin:javalin:7.2.3")
    implementation("org.slf4j:slf4j-simple:2.0.18")


}

tasks.test {
    useJUnitPlatform()
}
