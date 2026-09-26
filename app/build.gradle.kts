plugins {
    id("java")
    application
    id("com.gradleup.shadow") version "8.3.0"
    jacoco
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)

    reports {
        xml.required.set(true)
        csv.required.set(false)
        html.required.set(true)
    }
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

    compileOnly("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.projectlombok:lombok:1.18.34")

    testImplementation(platform("org.junit:junit-bom:5.13.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    testImplementation("org.junit.jupiter:junit-jupiter:5.11.4")
    testImplementation("org.assertj:assertj-core:3.27.3")
    testImplementation("io.javalin:javalin-testtools:7.2.3")


}

tasks.test {
    useJUnitPlatform()
}

jacoco {
    toolVersion = "0.8.15"
    reportsDirectory = layout.buildDirectory.dir("customJacocoReportDir")
}

tasks.jacocoTestReport {
    reports {
        xml.required = false
        csv.required = false
        html.outputLocation = layout.buildDirectory.dir("jacocoHtml")
    }
}
