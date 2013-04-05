package net.sf.chalkbox.build

import org.gradle.api.Plugin
import org.gradle.api.Project

public class ReportingPlugin implements Plugin<Project>{

    @Override
    public void apply(final Project project) {
        project.configurations { antClasspath }
        project.dependencies { antClasspath 'org.apache.ant:ant-junit:1.8.2' }
    }
}
