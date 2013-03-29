package net.sf.chalkbox.build;

import java.io.File;
import java.io.IOException;

public class GradleBuildFileGenerator {

    private final FileWriter fileWriter;

    public GradleBuildFileGenerator() {
        this.fileWriter = new FileWriter();
    }

    @SuppressWarnings("static-method")
    public void forJavaProject(final File file, final String name) {
        makingSureParentDirectoryExists(file, name);
        writeToFile(file, javaProjectContents(name).toString(), "build.gradle");
    }

    private void writeToFile(final File parent, final String contents,
            final String buildFile) {
        try {
            fileWriter().writeToFile(parent, contents, buildFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("static-method")
    public void forJavaLibProject(final File file, final String name) {
        makingSureParentDirectoryExists(file, name);
        writeToFile(file, javaLibProjectContents(name).toString(),
                "build.gradle");
    }

    private FileWriter fileWriter() {
        return fileWriter;
    }

    private static StringBuilder javaProjectContents(final String name) {
        final StringBuilder sb = new StringBuilder("project(':").append(name)
                .append("') {").append("\n")

                .append(tab()).append("apply from: \"$emmaPlugin\"")
                .append("\n")

                .append(tab()).append("apply from: \"$distributionPlugin\"")
                .append("\n").

                append("}");
        return sb;
    }

    private static StringBuilder javaLibProjectContents(final String name) {
        final StringBuilder sb = new StringBuilder("project(':").append(name)
                .append("') { prj ->").append("\n")

                .append(tab()).append("apply from: \"$libraryPlugin\"")
                .append("\n")

                .append(tab()).append("prj.ext.library='").append(name)
                .append(".jar'\n")

                .append(tab()).append("prj.ext.librarySources='").append(name)
                .append("-sources.jar'\n")

                .append("}");
        return sb;
    }

    private static String tab() {
        return "    ";
    }

    private static void makingSureParentDirectoryExists(final File file,
            final String name) {
        if (!file.exists()) {
            if (!file.mkdirs()) {
                throw new RuntimeException(
                        "Not able to create the parent directories for '"
                                + name + "'! Need to exit.");
            }
        }
    }
}
