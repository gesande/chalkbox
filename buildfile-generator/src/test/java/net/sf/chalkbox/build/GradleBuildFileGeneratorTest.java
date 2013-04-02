package net.sf.chalkbox.build;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

public class GradleBuildFileGeneratorTest {

    @SuppressWarnings("static-method")
    @Test
    public void forJavaProject() throws IOException {
        final GradleBuildFileGenerator generator = new GradleBuildFileGenerator();
        final String project = "javaproject";
        final File parent = new File("target", project);
        generator.forJavaProject(parent, project);
        assertEquals(
                "project(':javaproject') {\n    apply from: \"$emmaPlugin\"\n    apply from: \"$distributionPlugin\"\n}\n",
                readBuildFile(parent).toString());
    }

    private static StringBuilder readBuildFile(final File parent)
            throws FileNotFoundException, IOException {
        final BufferedReader bufferedReader = new BufferedReader(
                new FileReader(new File(parent, "build.gradle")));
        final StringBuilder sb = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
            sb.append(line).append("\n");
        }
        return sb;
    }

    @SuppressWarnings("static-method")
    @Test
    public void forJavaLibProject() throws IOException {
        final GradleBuildFileGenerator generator = new GradleBuildFileGenerator();
        final String project = "lib-1.0.0";
        final File parent = new File("target", project);
        generator.forJavaLibProject(parent, project);
        assertEquals(
                "project(':lib-1.0.0') { prj ->\n    apply from: \"$libraryPlugin\"\n    prj.ext.library='lib-1.0.0.jar'\n    prj.ext.librarySources='lib-1.0.0-sources.jar'\n}\n",
                readBuildFile(parent).toString());
    }

}
