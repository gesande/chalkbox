package net.sf.chalkbox;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;

public class Example {

    @SuppressWarnings("static-method")
    @Test
    public void produce() throws IOException {
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
                "example"));
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
            final String text) {
        sb.append("this is ").append(chalk.write(text)).append(" text")
                .append("\n");
    }
}
