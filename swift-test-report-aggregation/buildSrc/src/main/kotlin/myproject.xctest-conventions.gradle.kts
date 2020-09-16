plugins {
    id("xctest")
}

xctest {
    binaries.configureEach {
        // Disable the test report for the individual test task
        runTask.get().reports.html.isEnabled = false
    }
}

components.register("testReporting") {
    variant("binaryTestResultsElements") {
        providesDocumentation().set("test-report-data")
        tasks.withType<XCTest> {
            artifactFrom(binaryResultsDirectory)
        }
    }
}
