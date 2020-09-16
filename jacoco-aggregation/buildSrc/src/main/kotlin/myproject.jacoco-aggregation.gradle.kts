plugins {
    java
    jacoco
}

repositories {
    jcenter()
}

val sourcesPath = resolvers.configure("sourcesPath") {
    requiresDocumentation().set("source-folders")
    dependenciesFrom("implementation")
}
val coverageDataPath = resolvers.configure("coverageDataPath") {
    requiresDocumentation().set("jacoco-coverage-data")
    dependenciesFrom("implementation")
}

// Task to gather code coverage from multiple subprojects
val codeCoverageReport by tasks.registering(JacocoReport::class) {
    additionalClassDirs(configurations.runtimeClasspath.get())
    additionalSourceDirs(files(sourcesPath))
    executionData(files(coverageDataPath).filter { it.exists() })

    reports {
        // xml is usually used to integrate code coverage with
        // other tools like SonarQube, Coveralls or Codecov
        xml.isEnabled = true

        // HTML reports can be used to see code coverage
        // without any external tools
        html.isEnabled = true
    }
}

// Make JaCoCo report generation part of the 'check' lifecycle phase
tasks.check {
    dependsOn(codeCoverageReport)
}
