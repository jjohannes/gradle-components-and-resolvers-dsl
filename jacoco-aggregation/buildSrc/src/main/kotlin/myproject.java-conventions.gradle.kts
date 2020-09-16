plugins {
    java
    jacoco
}

version = "1.0.2"
group = "org.gradle.sample"

repositories {
    jcenter()
}

tasks.test {
    useJUnitPlatform()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

// Do not generate reports for individual projects
tasks.jacocoTestReport {
    enabled = false
}

components.named("java") {
    variant("transitiveSourcesElements") {
        providesDocumentation().set("source-folders")
        artifactsFrom(sourceSets.main.get().java.srcDirs)
        dependenciesFrom("implementation")
    }
    variant("coverageDataElements") {
        providesDocumentation().set("jacoco-coverage-data")
        artifactFrom(tasks.test.map { task ->
            task.extensions.getByType<JacocoTaskExtension>().destinationFile!!
        })
        dependenciesFrom("implementation")
    }
}

