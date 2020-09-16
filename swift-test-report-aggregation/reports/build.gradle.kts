val testReportDataPath = resolvers.configure("testReportDataPath") {
    requiresDocumentation().set("test-report-data")
    dependenciesFrom("testReportData")
}

dependencies {
    "testReportData"(project(":core"))
    "testReportData"(project(":util"))
}

tasks.register<TestReport>("testReport") {
    destinationDir = file("$buildDir/reports/allTests")

    // Use test results from testReportData configuration
    (testResultDirs as ConfigurableFileCollection).from(testReportDataPath)
}
