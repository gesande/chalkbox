package net.sf.chalkbox.build;

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.logging.StyledTextOutputFactory
import org.gradle.logging.StyledTextOutput.Style

public class JavaDevelopmentPlugin implements Plugin<Project> {

    @Override
    public void apply(final Project project) {

        project.task("createLibDirs") { Task task ->
            group 'Development'
            description 'Creates lib and lib-sources -directories under the given project.'
            doLast {
                def libDir = new File(task.project.projectDir, 'lib')
                libDir.mkdirs()
                def libSourcesDir = new File(task.project.projectDir, 'lib-sources')
                libSourcesDir.mkdirs()
            }
        }
        project.task("createJavaDirs") { Task task ->
            group 'Development'
            description 'Create directory structures for a Java project.'
            doLast {
                project.sourceSets*.java.srcDirs*.each { it.mkdirs() }
                project.sourceSets*.resources.srcDirs*.each { it.mkdirs() }
            }
        }
        project.task("addProjectToSvn") { Task task ->
            group 'Development'
            description 'Add project to SVN.'
            doLast {
                def out= ""
                new ByteArrayOutputStream().withStream { os ->
                    def result = project.parent.exec {
                        executable = 'svn'
                        args = [
                            'add',
                            "${project.name}"
                        ]
                        standardOutput = os
                    }
                    out = os.toString()
                }
                services.get(StyledTextOutputFactory).create("java-development.addProjectToSvn").withStyle(Style.Info).println("Project ${project.name} was added to SVN")
            }
        }
        project.task("buildGradleForJavaProject",type: ExportGradleBuildFileForNewJavaProject) { ExportGradleBuildFileForNewJavaProject task ->
            task.parent = task.project.projectDir
            task.projectName = task.project.name
        }

        project.task("buildGradleForJavaLibProject",type: ExportGradleBuildFileForJavaLibraryProject) { ExportGradleBuildFileForJavaLibraryProject task ->
            task.parent = task.project.projectDir
            task.projectName = task.project.name
        }
    }
}

