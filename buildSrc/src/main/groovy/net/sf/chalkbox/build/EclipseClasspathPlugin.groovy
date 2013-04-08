package net.sf.chalkbox.build

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.logging.StyledTextOutputFactory
import org.gradle.logging.StyledTextOutput.Style

class EclipseClasspathPlugin implements Plugin<Project>{

    @Override
    public void apply(final Project project) {
        project.apply { plugin: 'eclipse' }
        project.apply {
            project.eclipse.classpath.defaultOutputDir = project.file('target/classes')
        }

        project.task("removeTarget") { Task task ->
            group = 'IDE'
            description = "Deletes project specific eclipse target -directory."
            doLast {
                project.delete('target')
                services.get(StyledTextOutputFactory).create("EclipseClasspathPlugin.removeTarget").withStyle(Style.Info).println( "Target -directory deleted from project ${task.project.name}.")
            }
        }
    }
}
