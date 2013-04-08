package net.sf.chalkbox.build

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.bundling.Zip

class JavaProjectDistribution implements Plugin<Project>{

    @Override
    public void apply(final Project project) {
        project.getPlugins().apply("java")

        project.task('dist', type: Zip) { Zip task ->
            group = 'Distribution'
            description = "Makes the project specific distribution archive."
            task.dependsOn("jar")
            task.from project.jar
            task.from project.configurations.runtime
            task.exclude('junit-4.10-1.0.0.jar')
        }

        project.artifacts {
            archives project.dist
            archives project.sourcesJar
        }

        project.task('testCodeDist', type: Zip) { Zip task ->
            group = 'Distribution'
            description = "Makes the project specific test code distribution archive."
            task.dependsOn 'jar'
            task.from project.testSourcesJar
            task.from project.configurations.testRuntime
            task.exclude('junit-4.10-1.0.0.jar')
        }

        project.task('release', dependsOn: ['dist', 'sourcesJar']) {
            group = 'Distribution'
            description = "Defines the project specific release distributions."
        }

        project.task('testCodeRelease', dependsOn: [
            'testCodeDist',
            'testSourcesJar'
        ]) {
            group = 'Distribution'
            description = "Defines the project specific test code release distributions."
        }
    }
}
