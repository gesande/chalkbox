package net.sf.chalkbox.build;

import java.io.File;
import java.io.IOException;

public class AntBuildFileGenerator {

    private final FileWriter fileWriter;

    public AntBuildFileGenerator() {
        fileWriter = new FileWriter();
    }

    public void generate(final File parent, final String buildfileName,
            final String defaultTarget, final String... targets) {
        if (!parent.exists()) {
            throw new RuntimeException("Given parent directory doesn't exist!");
        }
        writeToFile(parent, contentsOfAntBuildFile(defaultTarget, targets)
                .toString(), buildfileName);
    }

    private void writeToFile(final File parent, final String contents,
            final String buildFile) {
        try {
            fileWriter().writeToFile(parent, contents, buildFile);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    private StringBuilder contentsOfAntBuildFile(final String defaultTarget,
            final String... targets) {
        final StringBuilder sb = new StringBuilder();
        sb.append("<!-- NOTE this is generated but versioned file -->").append(
                newLine());
        sb.append("<project name=\"gradle\" default=\"").append(defaultTarget)
                .append("\" basedir=\"..\\\">").append(newLine());
        sb.append(tab())
                .append("<description description=\"Build entrypoints\">")
                .append(newLine()).append(tab()).append("</description>")
                .append(newLine());
        sb.append(tab())
                .append("<!-- set global properties for this build -->")
                .append(newLine());
        sb.append(tab()).append("<property environment=\"env\" />")
                .append(newLine());
        sb.append(tab())
                .append("<property name=\"gradle.executable\" location=\"${env.GRADLE_HOME}/bin/gradle\" />")
                .append(newLine());

        if (defaultTarget != null && !defaultTarget.isEmpty()) {
            appendTarget(sb, defaultTarget);
        }
        for (final String target : targets) {
            appendTarget(sb, target);
        }
        return sb.append("</project>").append(newLine());
    }

    private void appendTarget(final StringBuilder sb, final String target) {
        sb.append(tab()).append("<target name=\"").append(target).append("\">")
                .append(newLine());
        sb.append(tab()).append(tab())
                .append("<exec executable=\"${gradle.executable}\" dir=\".\">")
                .append(newLine());
        sb.append(tab()).append(tab()).append(tab()).append("<arg value=\"")
                .append(target).append("\" />").append(newLine());
        sb.append(tab()).append(tab()).append("</exec>").append(newLine());
        sb.append(tab()).append("</target>").append(newLine());
    }

    private String newLine() {
        return "\n";
    }

    private static String tab() {
        return "\t";
    }

    private FileWriter fileWriter() {
        return this.fileWriter;
    }
}
