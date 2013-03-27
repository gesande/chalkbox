package net.sf.chalkbox;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;

public class Example {

    @SuppressWarnings("static-method")
    @Test
    public void chalk() throws IOException {
        final ChalkBox box = new ChalkBox();
        final StringBuilder sb = new StringBuilder();
        appendText(sb, box.black(), "black");
        appendText(sb, box.red(), "red");
        appendText(sb, box.green(), "green");
        appendText(sb, box.yellow(), "yellow");
        appendText(sb, box.blue(), "blue");
        appendText(sb, box.magenta(), "magenta");
        appendText(sb, box.cyan(), "cyan");
        appendText(sb, box.white(), "white");

        final FileOutputStream out = new FileOutputStream(new File("./target",
                "chalk-example"));
        final String content = sb.toString();
        System.out.println(content);
        try {
            out.write(content.getBytes());
        } finally {
            out.flush();
            out.close();
        }

    }

    @SuppressWarnings("static-method")
    @Test
    public void marker() throws IOException {
        final ChalkBox box = new ChalkBox();
        final StringBuilder sb = new StringBuilder();
        final Marker marker = new Marker();
        appendText(sb, marker.mark("marked plain"));
        appendText(sb, box.black(), marker, "marked");
        appendText(sb, box.red(), marker, "marked");
        appendText(sb, box.green(), marker, "marked");
        appendText(sb, box.yellow(), marker, "marked");
        appendText(sb, box.blue(), marker, "marked");
        appendText(sb, box.magenta(), marker, "marked");
        appendText(sb, box.cyan(), marker, "marked");
        appendText(sb, box.white(), marker, "marked");

        final FileOutputStream out = new FileOutputStream(new File("./target",
                "marker-example"));
        final String content = sb.toString();
        System.out.println(content);
        try {
            out.write(content.getBytes());
        } finally {
            out.flush();
            out.close();
        }

    }

    private static void appendText(final StringBuilder sb, final Chalk chalk,
            final Marker marker, final String text) {
        appendText(sb, marker.mark(chalk.write(text)));
    }

    private static void appendText(final StringBuilder sb, final Chalk chalk,
            final String text) {
        appendText(sb, chalk.write(text));
    }

    private static void appendText(final StringBuilder sb, final String text) {
        sb.append("this is ").append(text).append(" text").append("\n");
    }
}
