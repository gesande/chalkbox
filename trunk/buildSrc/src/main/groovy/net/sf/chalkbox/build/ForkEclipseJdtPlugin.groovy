package net.sf.chalkbox.build

import org.gradle.api.Plugin
import org.gradle.api.Project

class ForkEclipseJdtPlugin implements Plugin<Project>{

    @Override
    public void apply(final Project project) {
        project.extensions.create("forkJdt", ForkEclipseJdtExtension)
        project.getPlugins().apply("eclipse")
        project.apply {
            project.eclipseJdt {
                def jdtInputFile ="${project.properties.buildTemplates}/for/eclipse/org.eclipse.jdt.core.prefs"
                inputFile=project.file("${jdtInputFile}")
                doLast { println "Forked eclipseJdt.inputFile from ${jdtInputFile} for project '${project.name}'" }
            }
        }
    }
}
class ForkEclipseJdtExtension{
    String jdtInputFile
}
