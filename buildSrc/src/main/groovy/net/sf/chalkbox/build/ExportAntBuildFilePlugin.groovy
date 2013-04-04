package net.sf.chalkbox.build

import org.gradle.api.Plugin
import org.gradle.api.Project


public final class ExportAntBuildFilePlugin implements Plugin<Project>{

    @Override
    public void apply(final Project project) {
        project.task('exportAntBuildFile', type: ExportAntBuildFileTask ) { ExportAntBuildFileTask task ->
            group = 'Build'
            description = 'Creates a ant build file for the project which contains the most important targets.'
            task.parent = "buildSrc"
            task.buildFilename= "chalkbox.xml"
            task.defaultTarget= "continousBuild"
            task.targets = [
                "distributionPackage",
                "eclipseSettings",
                "exportAntBuildFile",
                "refreshBuildFileGenerator"
            ]
        }
    }
}
