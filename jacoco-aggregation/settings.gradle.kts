// Based on: https://docs.gradle.org/6.7-rc-1/samples/sample_jvm_multi_project_with_code_coverage.html

rootProject.name = "jacoco-aggregation"

// production code projects
include("application", "list", "utilities")

// reporting utility projects
include("code-coverage-report")
