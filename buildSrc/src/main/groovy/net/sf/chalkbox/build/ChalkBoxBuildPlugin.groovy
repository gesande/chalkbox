package net.sf.chalkbox.build

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.file.CopySpec
import org.gradle.api.tasks.GradleBuild

public class ChalkBoxBuildPlugin implements Plugin<Project>{

    @Override
    public void apply(Project project) {

        project.task("continousBuild", type: GradleBuild) { Task task ->
            group = 'Verification'
            description ='Continous build for the whole thing. Works also as a license to commit build target.'
            buildFile = 'build.gradle'
            tasks << 'clean'
            tasks << 'eclipseSettings'
            //tasks << 'refreshBuildFileGenerator'
            tasks << 'applySvnIgnoreFromGeneratedFile'
            tasks << 'exportAntBuildFile'
            tasks << 'chalkbox:continous'
            tasks << 'example:continous'
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
            group = 'Distribution'
            description = 'Distribution package for the whole thing including continous build.'
            buildFile = 'build.gradle'
            tasks << 'chalkbox:release'
            tasks << 'example:release'
            tasks << 'makeDistributionPackage'
            doLast { println "Distribution package ready to be uploaded to the repository." }
        }

        project.task("buildBuildFileGenerator", type: GradleBuild) { GradleBuild task ->
            group = 'Chalkbox build'
            description = 'Runs continous and release for buildfile-generator module.'
            tasks << 'buildfile-generator:continous'
            tasks << 'buildfile-generator:release'
        }

        project.task("refreshBuildFileGenerator",dependsOn: ['buildBuildFileGenerator']) { Task task ->
            group = 'Chalkbox build'
            description = 'Builds jar and sources-jar for buildfile-generator and copies the files to buildSrc lib/lib-sources directory.'
            doLast {
                def artifact = "buildfile-generator-1.1.0"
                def libs= project.file('buildfile-generator/build/libs')
                def buildSrcLib = project.file('buildSrc/lib')
                def libSources = project.file('buildSrc/lib-sources')
                project.copy { CopySpec spec ->
                    spec.from "${libs}/${artifact}.jar"
                    spec.into buildSrcLib
                }
                project. copy { CopySpec spec ->
                    spec.from "${libs}/${artifact}-sources.jar"
                    spec.into libSources
                }
                def mygradlebuildLib = project.file('my-gradle-build/lib')
                def mygradlebuildLibSources = project.file('my-gradle-build/lib-sources')
                project.copy { CopySpec spec ->
                    spec.from "${libs}/${artifact}.jar"
                    spec.into mygradlebuildLib
                }
                project. copy { CopySpec spec ->
                    spec.from "${libs}/${artifact}-sources.jar"
                    spec.into mygradlebuildLibSources
                }
            }
        }
    }
}
