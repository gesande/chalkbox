package net.sf.chalkbox.build

import org.gradle.api.Plugin
import org.gradle.api.Project


public final class ExportBuildFilePlugin implements Plugin<Project>{

    @Override
    public void apply(final Project project) {
        project.task('buildGradleForJavaProject', type: ExportGradleBuildFileForNewJavaProject)
        project.task('exportAntBuildFile', type: ExportAntBuildFileTask)
    }
}
