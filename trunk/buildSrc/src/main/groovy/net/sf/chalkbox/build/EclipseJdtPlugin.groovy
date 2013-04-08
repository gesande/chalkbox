package net.sf.chalkbox.build

import org.gradle.api.Plugin
import org.gradle.api.Project

class EclipseJdtPlugin implements Plugin<Project>{

    @Override
    public void apply(final Project project) {
        project.extensions.create("eclipseJdtSettings", EclipseJdtSettingsExtension)
        project.apply { plugin: 'eclipse' }
        project.apply { plugin: 'java' }
        /*
         project.apply {
         project.eclipseJdt.inputFile=project.file("${project.properties.buildTemplates}/for/eclipse/org.eclipse.jdt.core.prefs")
         }
         */
    }
}
class EclipseJdtSettingsExtension{
    String jdtInputFile
}
