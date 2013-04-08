package net.sf.chalkbox.build

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.GradleBuild
import org.gradle.logging.StyledTextOutputFactory
import org.gradle.logging.StyledTextOutput.Style


class NewJavaProjectPlugin implements Plugin<Project> {

    @Override
    public void apply(final Project project) {
        project.apply { "apply plugin: 'java'" }

        project.subprojects { apply plugin: NewJavaSubProjectPlugin  }

        project.task("newJavaProject", type: GradleBuild) { GradleBuild task ->
            group ='Development'
            description ="Creates a new Java project putting it already to SVN and applying some SVN ignore magic from predefined file which seem to be ${project.properties.svnIgnoreFile}. Needs -PnewJavaProject argument."
            def javaProject =  task.project.properties.newJavaProject
            String createJavaDirs = "${javaProject}:createJavaDirs"
            String eclipseSettingsFor = "${javaProject}:eclipseSettingsFor"
            String buildGradle = "${javaProject}:buildGradleForJavaProject"
            String svnAdd = "${javaProject}:addProjectToSvn"
            String svnIgnore = "${javaProject}:applySvnIgnore"
            task.tasks << createJavaDirs
            task.tasks << svnAdd
            task.tasks << svnIgnore
            task.tasks << eclipseSettingsFor
            task.tasks << buildGradle

            doLast {
                services.get(StyledTextOutputFactory).create("java-development.newJavaProject").withStyle(Style.Info).println("New Java project can now be found from ${task.project.buildDir}/${javaProject}")
            }
        }

        project.task("newJavaLibProject", type: GradleBuild) { GradleBuild task ->
            group ='Development'
            description ="Creates a new Java library project putting it already to SVN and applying some SVN ignore magic from predefined file which seem to be ${project.properties.svnIgnoreFile}. Needs -PnewJavaProject argument."
            def javaProject =  task.project.properties.newJavaProject
            String createJavaDirs = "${javaProject}:createJavaDirs"
            String eclipseSettingsFor = "${javaProject}:eclipseSettingsFor"
            String svnAdd = "${javaProject}:addProjectToSvn"
            String svnIgnore = "${javaProject}:applySvnIgnore"
            String createLibDirs="${javaProject}:createLibDirs"
            String buildGradle = "${javaProject}:buildGradleForJavaLibProject"

            task.tasks << createJavaDirs
            task.tasks << createLibDirs
            task.tasks << svnAdd
            task.tasks << svnIgnore
            task.tasks << eclipseSettingsFor
            task.tasks << buildGradle

            doLast {
                services.get(StyledTextOutputFactory).create("java-development.newJavaLibProject").withStyle(Style.Info).println("New Java project can now be found from ${task.project.buildDir}/${javaProject}")
            }
        }
    }
}
