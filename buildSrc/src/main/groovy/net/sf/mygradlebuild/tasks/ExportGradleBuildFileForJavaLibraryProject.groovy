package net.sf.mygradlebuild.tasks

import net.sf.chalkbox.build.GradleBuildFileGenerator;

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.logging.StyledTextOutputFactory
import org.gradle.logging.StyledTextOutput.Style


public class ExportGradleBuildFileForJavaLibraryProject extends DefaultTask{

    def String group ='Development'
    def String description= "Creates a template build.gradle for new java library project."

    def parent
    def projectName

    @TaskAction
    def exportBuildFile() {
        def GradleBuildFileGenerator generator =new GradleBuildFileGenerator()
        generator.forJavaLibProject(parent, projectName)
        printOutInfo("java-development.buildGradleForJavaLibProject", "Template for build.gradle successfully created for ${projectName}. Remember to fix lib and lib-sources to reflect the actual project.")
    }
    void printOutInfo(String taskName, String msg){
        services.get(StyledTextOutputFactory).create(taskName).withStyle(Style.Info).println(msg)
    }
}
