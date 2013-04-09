package net.sf.chalkbox;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

public class MarkerExample {
    public static void main(String[] args) throws IOException {
        marker();
    }

    public static void marker() throws IOException {
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
            out.write(content.getBytes(Charset.defaultCharset()));
        } finally {
            out.flush();
            out.close();
        }
    }

    private static void appendText(final StringBuilder sb, final Chalk chalk,
            final Marker marker, final String text) {
        appendText(sb, marker.mark(chalk.write(text)));
    }

    private static void appendText(final StringBuilder sb, final String text) {
        sb.append("this is ").append(text).append(" text").append("\n");
    }

}
