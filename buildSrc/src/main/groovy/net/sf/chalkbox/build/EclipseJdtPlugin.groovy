package net.sf.chalkbox.build

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.plugins.ide.eclipse.EclipsePlugin
import org.gradle.plugins.ide.eclipse.model.EclipseJdt

class EclipseJdtPlugin implements Plugin<Project>{

    @Override
    public void apply(final Project project) {
        project.extensions.create("eclipseJdtSettings", EclipseJdtSettingsExtension)
        project.getPlugins().apply("eclipse")
        //        def EclipsePlugin eclipsePlugin = project.getPlugins().apply("eclipse")
        //        eclipsePlugin.getModel().jdt { EclipseJdt jdt ->
        //            jdt. inputFile = project.file("${project.properties.buildTemplates}/for/eclipse/org.eclipse.jdt.core.prefs")
        //        }

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
