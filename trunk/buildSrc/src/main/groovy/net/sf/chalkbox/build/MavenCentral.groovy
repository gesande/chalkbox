package net.sf.chalkbox.build

import org.gradle.api.Plugin
import org.gradle.api.Project

class MavenCentral implements Plugin<Project>{

    @Override
    public void apply(final Project project) {
        project.allprojects { repositories { mavenCentral() } }
    }
}