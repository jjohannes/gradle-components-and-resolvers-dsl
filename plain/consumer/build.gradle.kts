repositories {
    jcenter()
}

resolvers.configure("binaryPath") {
    requiresLibrary().set("jar")
    dependenciesFrom("package")
}
resolvers.configure("sourcesPath") {
    requiresDocumentation().set("sources")
    dependenciesFrom("package")
}
resolvers.configure("sourceFoldersPath") {
    requiresDocumentation().set("source-folders")
    dependenciesFrom("package")
    allowEmptyMatches().set(true)
}

dependencies {
    "package"(project(":producer"))
}

// Tasks:

val bundleBinaries = tasks.register<Zip>("bundleBinaries") {
    archiveBaseName.set("binaries")
    from(resolvers["binaryPath"])
    destinationDirectory.set(layout.buildDirectory.dir("lib"))
}

val bundleSources = tasks.register<Zip>("bundleSources") {
    archiveBaseName.set("sources")
    from(resolvers["sourcesPath"])
    destinationDirectory.set(layout.buildDirectory.dir("lib"))
}

val bundleSourceFolders = tasks.register<Zip>("bundleSourceFolders") {
    archiveBaseName.set("source-folders")
    from(resolvers["sourceFoldersPath"])
    destinationDirectory.set(layout.buildDirectory.dir("lib"))
}

tasks.register("buildAll") {
   dependsOn(bundleBinaries, bundleSources, bundleSourceFolders)
}