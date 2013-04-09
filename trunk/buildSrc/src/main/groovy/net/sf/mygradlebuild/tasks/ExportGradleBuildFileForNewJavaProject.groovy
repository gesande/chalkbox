package net.sf.mygradlebuild.tasks;

import net.sf.mygradlebuild.GradleBuildFileGenerator

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.logging.StyledTextOutputFactory
import org.gradle.logging.StyledTextOutput.Style


public class ExportGradleBuildFileForNewJavaProject extends DefaultTask{

    def String group ='Development'
    def String description ="Creates a template build.gradle for new java project."

    def parent
    def projectName

    @TaskAction
    def exportBuildFile() {
        def GradleBuildFileGenerator generator =new GradleBuildFileGenerator()
        generator.forJavaProject(parent, projectName)
        services.get(StyledTextOutputFactory).create("buildGradleForJavaProject").withStyle(Style.Info).println("Build file build.gradle successfully created for project '${projectName}'.")
    }
}
