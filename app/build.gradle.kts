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
    implementation("gg.jte:jte:3.2.4")
    implementation("io.javalin:javalin-rendering-jte:7.2.3")

    implementation("org.slf4j:slf4j-simple:2.0.18")

    implementation("org.postgresql:postgresql:42.7.13")
    implementation("com.h2database:h2:2.2.220")
    implementation("com.zaxxer:HikariCP-java7:2.4.13")


}

tasks.test {
    useJUnitPlatform()
}
