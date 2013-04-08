package net.sf.chalkbox.build

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.GradleBuild

public class ChalkBoxBuildPlugin implements Plugin<Project>{

    @Override
    public void apply(Project project) {

        project.task("continousBuild", type: GradleBuild) { Task task ->
            group = 'Chalkbox verification'
            description ='Continous build for the whole thing. Works also as a license to commit build target.'
            buildFile = 'build.gradle'
            tasks << 'clean'
            //tasks << 'applySvnIgnore'
            tasks << 'eclipseSettings'
            tasks << 'refreshBuildFileGenerator'
            tasks << 'exportAntBuildFile'
            tasks << 'chalkbox:continous'
            tasks << 'chalkbox:dist'
            tasks << 'example:dist'

            tasks << 'aggregateTestReport'
            tasks << 'aggregateJDependReport'
            tasks << 'aggregateCoverageReport'
            tasks << 'aggregateFindbugsReport'
            tasks << 'archiveAggregateReports'
            doLast { println "Continous build passed, good work!" }
        }

        project.task("distributionPackage", type: GradleBuild, dependsOn: ['continousBuild']) { Task task ->
            group = 'Chalkbox distribution'
            description = 'Distribution package for the whole thing including continous build.'
            buildFile = 'build.gradle'
            tasks << 'chalkbox:release'
            tasks << 'example:release'
            tasks << 'makeDistributionPackage'
            doLast { println "Distribution package ready to be uploaded to the repository." }
        }

        project.task("buildBuildFileGenerator", type: GradleBuild) { GradleBuild task ->
            group = 'Chalkbox build'
            description = 'Runs continous and release to buildfile-generator module.'

            tasks << 'buildfile-generator:continous'
            tasks << 'buildfile-generator:release'
        }

        project.task("refreshBuildFileGenerator",dependsOn: ['buildBuildFileGenerator']) { Task task ->
            group = 'Chalkbox build'
            description = 'Builds jar and sources-jar for buildfile-generator and copies the files to buildSrc lib/lib-sources directory.'
            doLast {
                def artifact = "buildfile-generator-1.0.0"
                def libs= project.file('buildfile-generator/build/libs')
                def lib = project.file('buildSrc/lib')
                def libSources = project.file('buildSrc/lib-sources')
                project.copy {
                    from "${libs}/${artifact}.jar"
                    into lib
                }
                project. copy {
                    from "${libs}/${artifact}-sources.jar"
                    into libSources
                }
            }
        }
    }
}
