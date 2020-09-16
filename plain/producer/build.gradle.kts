repositories {
    jcenter()
}

dependencies {
    // use something published with GMM
    "comp"("org.junit.jupiter:junit-jupiter-api:5.7.0")
}

val compile = tasks.register<MyCompileTask>("compile") {
    sources.set(layout.projectDirectory.dir("src"))
    target.set(layout.buildDirectory.dir("bin"))
}

components.register("main") {
    variant("compiled") {
        providesLibrary().set("jar")
        artifactFrom(compile)
        dependenciesFrom("comp")
    }
    variant("sources") {
        providesDocumentation().set("sources")
        artifactFrom(compile.flatMap { it.sources })
        dependenciesFrom("comp")
    }
    variant("sourceFolders") {
        providesDocumentation().set("source-folders")
        artifactFrom(compile.flatMap { it.sources })
        dependenciesFrom("comp")
    }
}

abstract class MyCompileTask : DefaultTask() {

    @get:InputDirectory
    abstract val sources: DirectoryProperty

    @get:OutputDirectory
    abstract val target: DirectoryProperty

    @TaskAction
    fun compile() {
        target.asFileTree.visit {
            file.delete()
        }
        sources.asFileTree.visit {
            val targetFile = target.get().file("$path.compiled").asFile
            targetFile.appendText("// This was compiled\n\n")
            targetFile.appendText(file.readText())
        }
    }
}