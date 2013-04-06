package net.sf.chalkbox.build

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.file.FileTree
import org.gradle.api.tasks.bundling.Compression
import org.gradle.api.tasks.bundling.Tar
import org.gradle.logging.StyledTextOutputFactory
import org.gradle.logging.StyledTextOutput.Style

public class ReportingPlugin implements Plugin<Project>{

    @Override
    public void apply(final Project project) {
        project.extensions.create("reportingPlugin", ReportingPluginExtension)

        project.configurations { antClasspath }
        project.dependencies { antClasspath 'org.apache.ant:ant-junit:1.8.2' }

        project.task ("reportingOptions") << {
            println project.reportingPlugin.reportDir
            println project.reportingPlugin.toolsDir
        }
        project.task ("aggregateTestReport") {
            group = 'Reporting'
            description = "Makes aggregate test report with ant-junit."

            doLast {
                ClassLoader antClassLoader = org.apache.tools.ant.Project.class.classLoader
                project.configurations.antClasspath.each { File f ->
                    antClassLoader.addURL(f.toURI().toURL())
                }
                def targetDir = new File("${project.reportingPlugin.reportDir}", 'junit')
                targetDir.mkdirs()
                def resultsDir=targetDir.getPath()
                println 'Creating test report...'

                ant.taskdef(
                        name: 'junitreport',
                        classname: 'org.apache.tools.ant.taskdefs.optional.junit.XMLResultAggregator',
                        classpath: project.configurations.antClasspath.asPath
                        )
                ant.junitreport(todir: resultsDir) {
                    fileset(dir: "${project.projectDir}", includes: '**/build/test-results/TEST-*.xml')
                    report(todir: targetDir, format: "frames")
                }

                def outputFactory = services.get(StyledTextOutputFactory).create("reporting.aggregateTestReport")
                outputFactory.withStyle(Style.Info).println("Aggregate test report can be found from file://${project.reportingPlugin.reportDir}/junit/index.html")
            }
        }

        project.task("aggregateCoverageReport") { Task task ->
            group = 'Reporting'
            description = "Makes aggregate coverage report with emma."
            doLast {
                def targetDir = new File("${project.reportingPlugin.reportDir}", 'emma')
                targetDir.deleteDir()
                targetDir.mkdirs()
                def List<String> inArgs= new ArrayList<String>()
                def List<String> sourcePathArgs= new ArrayList<String>()
                for (Project sub : task.project.getSubprojects()) {
                    def FileTree emmaTree = sub.fileTree('build').include('tmp/emma/metadata.emma').include('tmp/emma/instr/metadata.emma')
                    for(File emmaFile : emmaTree.files) {
                        inArgs.add("-input")
                        inArgs.add(emmaFile)
                    }
                    def String sourceDir = sub.fileTree("src/main/java").getDir()
                    sourcePathArgs.add("-sourcepath")
                    sourcePathArgs.add(sourceDir)
                }
                def List<String> arguments = new ArrayList<String>()
                arguments.add("report")
                arguments.add("-report")
                arguments.add("html")
                arguments.addAll(inArgs)
                arguments.addAll(sourcePathArgs)
                arguments.add("-Dreport.out.file=${targetDir}/coverage.html")

                println 'Creating coverage report...'
                def exit = project.javaexec {
                    classpath "${project.reportingPlugin.toolsDir}/emma-2.1.5320-lib/emma.jar"
                    main = 'emma'
                    args = arguments
                }
                def outputFactory = services.get(StyledTextOutputFactory).create("reporting.aggregateCoverageReport")
                outputFactory.withStyle(Style.Info).println("Coverage report can be found from file://${targetDir}/coverage.html)")
            }
        }
        project.task("aggregateJDependReport") {
            group = 'Reporting'
            description = "Makes aggregate jdepend report with tattletale."
            doLast {
                def targetDir = new File("$project.reportingPlugin.reportDir", 'jdepend')
                targetDir.deleteDir()
                targetDir.mkdirs()
                def jarsDir= new File("${project.reportingPlugin.reportDir}/analyzed-jars")
                jarsDir.deleteDir()
                jarsDir.mkdirs()
                def FileTree jars = project.fileTree("${project.projectDir}").include('**/build/libs/*.jar')
                def FileTree libs = project.fileTree("${project.projectDir}").exclude("${project.reportingPlugin.toolsDir}").include('**/lib/*.jar')
                project.copy {
                    from jars.getFiles() + libs.getFiles()
                    into "$jarsDir"
                }
                println 'Creating JDepend report...'
                new ByteArrayOutputStream().withStream { os ->
                    def result = project.exec {
                        executable = 'java'
                        args =[
                            '-Xmx1024m',
                            '-jar',
                            "$project.reportingPlugin.toolsDir/tattletale-1.1.2.Final/tattletale.jar",
                            "${jarsDir}",
                            "${targetDir}"
                        ]
                        standardOutput = os
                    }
                    def outputFactory = services.get(StyledTextOutputFactory).create("reporting.aggregateJDependReport")
                    outputFactory.withStyle(Style.Info).println("JDepend report can be found from file://${targetDir}/index.html)")
                }
            }
        }
        project.task("aggregateFindBugsReport") { Task task ->
            group ='Reporting'
            description = 'Makes aggregate findbugs report.'
            doLast {
                def targetDir = new File("${project.reportingPlugin.reportDir}", 'findbugs')
                targetDir.deleteDir()
                targetDir.mkdirs()
                def String jreHome = "${System.getenv('JAVA_HOME')}/jre"
                println 'Creating FindBugs report...'
                def List<String> arguments = new ArrayList<String>()
                arguments.add('-javahome')
                arguments.add(jreHome)
                arguments.add('-textui')
                arguments.add('-html')
                arguments.add('-output')
                arguments.add( "${targetDir}/index.html")
                arguments.add('-onlyAnalyze')
                arguments.add('net.sf.perftence.-')
                //TODO: this doesn't work, find out why...
                //        arguments.add('-sourcepath')
                //        def List<String> sourcePathArgs= new ArrayList<String>()
                //        def FileTree mainJava = fileTree("$projectDir").include('**/src/main/java')
                //        for(File srcDir : mainJava) {
                //            println srcDir
                //            arguments.add(srcDir)
                //        }

                arguments.add('-auxclasspath')

                def StringBuilder auxClasspath = new StringBuilder()
                def FileTree libs = project.fileTree("${project.projectDir}").exclude("${project.reportingPlugin.toolsDir}").include('**/lib/*.jar')
                def FileTree jreLibs = project.fileTree("${jreHome}/lib/").include('*.jar')
                def FileTree extLibs = project.fileTree("${project.reportingPlugin.toolsDir}/ext-libs-for-findbugs/").include('*.jar')
                for(File libJar : libs){
                    auxClasspath.append(libJar.getPath())
                    auxClasspath.append(File.pathSeparator)
                }
                for(File jar : jreLibs) {
                    auxClasspath.append(jar.getPath())
                    auxClasspath.append(File.pathSeparator)
                }
                for(File jar : extLibs) {
                    auxClasspath.append(jar.getPath())
                    auxClasspath.append(File.pathSeparator)
                }
                arguments.add(auxClasspath.toString())

                def FileTree jars = project.fileTree("${project.projectDir}").include('**/build/libs/*.jar')
                for(File jarFile : jars ){
                    arguments.add(jarFile.getPath())
                }

                new ByteArrayOutputStream().withStream { os ->
                    def result = project.exec {
                        executable = "${project.reportingPlugin.toolsDir}/findbugs-2.0.2/bin/findbugs"
                        args = arguments
                        standardOutput = os
                    }
                }
                def outputFactory = services.get(StyledTextOutputFactory).create("reporting.aggregateFindBugsReport")
                outputFactory.withStyle(Style.Info).println("Findbugs report can be found from file://${targetDir}/index.html)")
            }
        }
        project.task("archiveAggregateReports", type: Tar)  { Tar task ->
            group = 'Archive'
            description = 'Archive aggregate reports including junit tests/pmd/findbugs/jdepend'
            from project.properties.reportDir
            // Set destination directory.
            task.destinationDir = project.properties.cacheDir
            // Set filename properties.
            task.baseName = "report-artifacts-"+ project.properties.artifactVersion
            extension = 'tar.gz'
            compression = Compression.GZIP
            doLast {
                def String tarFile = "${project.properties.cacheDir}/report-artifacts-${project.properties.artifactVersion}.tar.gz"
                printOutInfo("artifact.archiveAggregateReports", "Report artifact archive can be found from file://$tarFile")
            }
        }
    }
}
public class ReportingPluginExtension {
    def String toolsDir
    def String reportDir
}

